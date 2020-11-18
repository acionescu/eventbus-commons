/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.event.eventbus.peers.agents;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.StrictChannelMatchCondition;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventHeader;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.PassthroughCustomEventContextListenerFactory;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeersManagerAgent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.peers.vo.PeerLeavingData;

public class RemotePeersAgent extends PeersManagerAgent {
    public static final String TYPE = "RemotePeersAgent";
    /**
     * Accepted remote peers gateways
     */
    private Map<String, GatewayPeerController> gateways = new HashMap<>();

    /**
     * If incoming events from remote peers satisfy this condition, we'll automatically create a RemotePeerManager to
     * handle this peer
     */
    private Condition autoCreateCondition;
    private String channel = "WSS_WEB_V0";

    @Override
    protected void config() {
	context.logger().debug("RemotePeersAgent init");
    }

    @Override
    protected void registerHandlers() {

	FilteringEventProcessor processor = new FilteringEventProcessor(
		new PassthroughCustomEventContextListenerFactory());

	context.registerPeerEventProcessor(new StrictChannelMatchCondition(channel), (c) -> {
	    processor.processEvent(c);
	});

	processor.addEventHandler(PeerAcceptedEvent.class, (c) -> {
	    handleNewRemotePeer((PeerEventContext<PeerAcceptedEvent>) c);
	});

	processor.addEventHandler((c) -> {
	    handleRemotePeerEvent((PeerEventContext<Event>) c);
	});

	/* listen for remote peers leaving */
	processor.addEventHandler(PeerLeavingEvent.class, (c) -> {
	    handleRemotePeerLeaving((PeerEventContext<PeerLeavingEvent>) c);
	});

	/* listen for gateway peers leaving */

	context.registerPeerEventProcessor(PeerLeavingEvent.class, (c) -> {
	    handleGatewayLeaving((PeerEventContext<PeerLeavingEvent>) c);
	});
	
    }

    private void handleGatewayLeaving(PeerEventContext<PeerLeavingEvent> c) {
	PeerLeavingEvent event = c.getEvent();
	if (event.getLastRelay() == null) {
	    /* make sure that this comes from us */
	    PeerInfo peerInfo = event.getData().getPeerInfo();
	    String gatewayPeerId = peerInfo.getPeerId();

	    GatewayPeerController gatewayController = gateways.remove(gatewayPeerId);

	    if (gatewayController != null) {
		gatewayController.onPeerLeaving(c);
	    }
	}
    }

    private void handleRemotePeerLeaving(PeerEventContext<PeerLeavingEvent> c) {
	PeerLeavingEvent event = c.getEvent();

	/* use the last relay to make sure we get the direct peer id */
	String lastRelay = event.getLastRelay();

	if (lastRelay == null) {
	    /* ignore events from local */
	    return;
	}

	PeerLeavingData data = event.getData();

	if (data == null) {
	    return;
	}

	PeerInfo peerInfo = data.getPeerInfo();

	if (peerInfo == null) {
	    return;
	}

	GatewayPeerController gatewayPeerController = gateways.get(lastRelay);

	if (gatewayPeerController != null) {
	    /* if we have a controller for this gateway, delegate to it */
	    gatewayPeerController.removeRemotePeer(c);
	    
	}
	/* mark this as handled, we don't want peers to inject this type of event*/
	event.setHandled();
    }

    private void handleRemotePeerEvent(PeerEventContext<Event> c) {
	Event event = c.getEvent();
	if (context.logger().isDebugEnabled()) {
	    context.logger().debug(TYPE + ": remote agent got event: " + event.toJson());
	}

	EventHeader header = event.getHeader();
	if (header.getRelayedBy().size() > 1) {
	    /* if this event was relayed from another peer, then pass it to the gateway controller, if exists */
	    String lastRelay = header.getLastRelay();
	    GatewayPeerController gatewayPeerController = gateways.get(lastRelay);
	    boolean handled = false;
	    if (gatewayPeerController != null) {
		context.logger().debug(TYPE + ": Send event " + event.getEt() + " to gateway controller " + lastRelay);

		handled = gatewayPeerController.handleRemotePeerEvent(c);

	    }

	    if (!handled && autoCreateCondition != null && autoCreateCondition.test(c)) {
		context.logger().debug(TYPE + ": Autocreating remote peer controller from event " + event.getEt()
			+ " on gateway controller " + lastRelay+" root event "+event.getHeader().getRootEvent());

		/* if we don't have a controller for this peer and we're allowed to automatically create one, do it */
		RemotePeerEventContext remotePeerEventContext = new RemotePeerEventContext(c,
			new PeerInfo(event.from(), header.getSourceType(), null));

		gatewayPeerController = checkAndAddNewRemotePeer(remotePeerEventContext);
		if(gatewayPeerController != null) {
		    /* process the event */
		    context.logger().debug("handling remote event after creating peer controller "+event.getEt());
		    gatewayPeerController.handleRemotePeerEvent(c);
		}
	    }
	}

    }

    private void handleNewRemotePeer(PeerEventContext<PeerAcceptedEvent> c) {
	PeerAcceptedEvent event = c.getEvent();

	/* use the last relay to make sure we get the direct peer id */
	String lastRelay = event.getLastRelay();

	if (event.getHeader().getRelayedBy().size() <= 1) {
	    /* ignore events from local */
	    return;
	}

	PeerInfo data = event.getData();

	if (data == null) {
	    return;
	}

	RemotePeerEventContext remotePeerEventContext = new RemotePeerEventContext(c, data);

	checkAndAddNewRemotePeer(remotePeerEventContext);
	
	/* mark the event as handled to block further posting */
	event.setHandled();
    }

    private GatewayPeerController checkAndAddNewRemotePeer(RemotePeerEventContext c) {
	PeerEventContext<? extends Event> triggerEventContext = c.getTriggerEventContext();
	Event event = triggerEventContext.getEvent();

	/* use the last relay to make sure we get the direct peer id */
	String lastRelay = event.getLastRelay();

	if (lastRelay == null) {
	    /* ignore events from local */
	    return null;
	}

	if (lastRelay.equals(event.from())) {
	    /* ignore events coming from direct peers */
	    return null;
	}

	Event rootEvent = event.getHeader().getRootEvent();
	if (rootEvent == null) {
	    /* we're looking for events that came from a peer */
	    return null;
	}
	context.logger().info(TYPE + ": Got new remote peer from root event: " + rootEvent.toJson());

	GatewayPeerController gatewayPeerController = gateways.get(lastRelay);

	/* add the gateway if is not present */
	if (gatewayPeerController == null) {
	    gatewayPeerController = new GatewayPeerController(context, triggerEventContext.getPeerManager());
	    gateways.put(lastRelay, gatewayPeerController);
	    context.logger().info("Created gateway for relay " + lastRelay);
	}
	/* call add new remote peer */
	gatewayPeerController.addNewRemotePeer(c);

	return gatewayPeerController;
    }

    public Condition getAutoCreateCondition() {
	return autoCreateCondition;
    }

    public void setAutoCreateCondition(Condition autoCreateCondition) {
	this.autoCreateCondition = autoCreateCondition;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

}
