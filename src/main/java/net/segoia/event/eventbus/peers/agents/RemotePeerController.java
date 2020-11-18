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

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.DefaultEventRelay;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeersAgentContext;
import net.segoia.event.eventbus.peers.RemotePeerManager;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;

public class RemotePeerController extends PeersAgentController {
    public static final String TYPE="RemotePeerController";
    private RemotePeerDataContext data;
    private RemotePeerManager remotePeerManager;
    private GatewayPeerController gatewayController;

    public RemotePeerController(PeersAgentContext context, RemotePeerDataContext data, GatewayPeerController gatewayController) {
	super(context,false);
	this.data = data;
	this.gatewayController = gatewayController;
	init();
    }

    @Override
    protected void config() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerHandlers() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void initController() {
	/* we have to create a remote peer manager here */
	 remotePeerManager = context.getRemotePeer(data);
	 if(remotePeerManager != null) {
	     if(context.isDebugEnabled()) {
		 context.logger().debug("associating existent remote peer manager to remote controller "+data.getFullRemotePeerPath());
	     }
	     data = (RemotePeerDataContext)remotePeerManager.getPeerContext();
	     
	 }
	 
	 /* set this as transceiver */
	 data.setTransceiver(this);
	 DefaultEventRelay newRelay = new DefaultEventRelay(data.getPeerId(), this);
	 data.setRelay(newRelay);
	 newRelay.setRemoteEventListener(remotePeerManager);
    }

    
    public boolean handleRemotePeerEvent(PeerEventContext<Event> c) {
	Event event = c.getEvent();
	
	if(context.logger().isDebugEnabled()) {
	    context.logger().info(TYPE+" -> "+data.getFullRemotePeerPath()+" handle "+event.toJson());
	}
	
	if(remotePeerManager != null) {
	    remotePeerManager.onPeerEvent(event);
	    event.setHandled();
	    return true;
	}
	else {
	    context.logger().error("No remote peer manager found for "+data.getFullRemotePeerPath());
	}
	return false;
    }

    @Override
    protected void onPeerLeaving(PeerEventContext<PeerLeavingEvent> c) {
	remotePeerManager.onPeerLeaving(c.getEvent().getData().getReason());	
    }

    public RemotePeerDataContext getData() {
        return data;
    }

    @Override
    public void start() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void terminate() {
	gatewayController.terminatePeerController(this);
    }

    @Override
    public void sendData(byte[] data) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String getChannel() {
	// TODO Auto-generated method stub
	return null;
    }
}
