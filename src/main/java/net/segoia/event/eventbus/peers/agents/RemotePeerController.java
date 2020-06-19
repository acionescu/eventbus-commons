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
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeersAgentContext;
import net.segoia.event.eventbus.peers.RemotePeerManager;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;

public class RemotePeerController extends PeersAgentController {
    public static final String TYPE="RemotePeerController";
    private RemotePeerDataContext data;

    public RemotePeerController(PeersAgentContext context, RemotePeerDataContext data) {
	super(context,false);
	this.data = data;
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
	context.addRemotePeer(data);
    }

    
    public void handleRemotePeerEvent(PeerEventContext<Event> c) {
	RemotePeerManager remotePeerManager = data.getRemotePeerManager();
	Event event = c.getEvent();
	
	if(context.logger().isDebugEnabled()) {
	    context.logger().info(TYPE+" -> "+data.getPeerId()+" handle "+event.toJson());
	}
	
	if(remotePeerManager != null) {
	    remotePeerManager.onPeerEvent(event);
	    event.setHandled();
	}
	
    }

    @Override
    protected void terminate(PeerEventContext<PeerLeavingEvent> c) {
	data.getRemotePeerManager().onPeerLeaving(c.getEvent().getData().getReason());	
    }
}
