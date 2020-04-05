package net.segoia.event.eventbus.peers.agents;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.conditions.StrictChannelMatchCondition;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventHeader;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.PassthroughCustomEventContextListenerFactory;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeersManagerAgent;
import net.segoia.event.eventbus.peers.events.NewPeerEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.vo.PeerInfo;

public class RemotePeersAgent extends PeersManagerAgent {
    /**
     * Accepted remote peers gateways
     */
    private Map<String, GatewayPeerController> gateways=new HashMap<>();

    @Override
    protected void config() {
	System.out.println("RemotePeersAgent init");

    }

    @Override
    protected void registerHandlers() {

	FilteringEventProcessor processor = new FilteringEventProcessor(
		new PassthroughCustomEventContextListenerFactory());

	context.registerPeerEventProcessor(new StrictChannelMatchCondition("WSS_WEB_V0"), (c) -> {
	    processor.processEvent(c);
	});

	processor.addEventHandler(PeerAcceptedEvent.class, (c) -> {
	    handleNewRemotePeer((PeerEventContext<PeerAcceptedEvent>) c);
	});

	processor.addEventHandler((c) -> {
	    handleRemotePeerEvent((PeerEventContext<Event>) c);
	});

    }
    
    private void handleRemotePeerEvent(PeerEventContext<Event> c) {
	Event event = c.getEvent();
	context.logger().info("remote agent got event: " + event.toJson());
	
	EventHeader header = event.getHeader();
	if(header.getRelayedBy().size() > 1) {
	    /* if this event was relayed from another peer, then pass it to the gateway controller, if exists */
	    String lastRelay = header.getLastRelay();
	    GatewayPeerController gatewayPeerController = gateways.get(lastRelay);
	    if(gatewayPeerController != null) {
		context.logger().info("Send event "+event.getEt() +" to gateway controller "+lastRelay);
		gatewayPeerController.handleRemotePeerEvent(c);
	    }
	}
	
    }

    private void handleNewRemotePeer(PeerEventContext<PeerAcceptedEvent> c) {
	PeerAcceptedEvent event = c.getEvent();
	
	/* use the last relay to make sure we get the direct peer id */
	String lastRelay = event.getLastRelay();
	
	if(lastRelay == null) {
	    /*ignore events from local */
	    return;
	}
	
	PeerInfo data = event.getData();
	
	if(data == null) {
	    return;
	}
	
	 if(lastRelay.equals(data.getPeerId())) {
	    /* ignore events coming from direct peers */
	    return;
	}
	
	GatewayPeerController gatewayPeerController = gateways.get(lastRelay);
	
	/* add the gateway if is not present */
	if(gatewayPeerController == null) {
	    gatewayPeerController = new GatewayPeerController(context, c.getPeerManager());
	    gateways.put(lastRelay, gatewayPeerController);
	    context.logger().info("Created gateway for relay "+lastRelay);
	}
	/* call add new remote peer */
	gatewayPeerController.addNewRemotePeer(c);
	
	/* mark the event as handled to blok further posting */
	event.setHandled();
    }

}
