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

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.PeersAgentContext;
import net.segoia.event.eventbus.peers.events.NewPeerEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.peers.vo.PeerLeavingData;
import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;

public class GatewayPeerController extends PeersAgentController{
    private PeerManager gatewayPeerManager;
    /**
     * Remote peers handled by this gateway
     */
    private Map<String, RemotePeerController> remotePeers=new HashMap<>();
    

    public GatewayPeerController(PeersAgentContext context, PeerManager gatewayPeerManager) {
	super(context,false);
	this.gatewayPeerManager = gatewayPeerManager;
	init();
    }

    @Override
    protected void config() {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void initController() {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void registerHandlers() {
	// TODO Auto-generated method stub
	
    }
    
    public void handleRemotePeerEvent(PeerEventContext<Event> c) {
	Event event = c.getEvent();
	String remotePeerId = event.from();
	RemotePeerController remotePeerController = remotePeers.get(remotePeerId);
	if(remotePeerController != null) {
	    context.logger().info("Send event "+event.getEt()+" to remote peer controller "+remotePeerId);
	    remotePeerController.handleRemotePeerEvent(c);
	}
    }

    public void addNewRemotePeer(PeerEventContext<PeerAcceptedEvent> c) {
	PeerAcceptedEvent event = c.getEvent();
	PeerInfo peerInfo = event.getData();
	String remotePeerId = peerInfo.getPeerId();
	
	if(!remotePeers.containsKey(remotePeerId)) {
	    RemotePeerDataContext remotePeerDataContext = new RemotePeerDataContext(c);
	    RemotePeerController remotePeerController = new RemotePeerController(context, remotePeerDataContext);
	    remotePeers.put(remotePeerId, remotePeerController);
	    context.logger().info("Created remote peer controller for "+ remotePeerDataContext.getFullRemotePeerPath());
	}
	
    }
    
    public void  removeRemotePeer(PeerEventContext<PeerLeavingEvent> c) {
	PeerLeavingEvent event = c.getEvent();
	PeerLeavingData data = event.getData();
	PeerInfo peerInfo = data.getPeerInfo();
	
	String remotePeerId = peerInfo.getPeerId();
	
	RemotePeerController remotePeerController = remotePeers.remove(remotePeerId);
	
	if(remotePeerController != null) {
	    context.logger().info(gatewayPeerManager.getPeerId()+": Terminating remote peer controller "+ remotePeerId);
	    remotePeerController.terminate(c);
	}
    }

    @Override
    protected void terminate(PeerEventContext<PeerLeavingEvent> c) {
	context.logger().info("Terminating gateway "+c.getEvent().getData().getPeerInfo().getPeerId()+" with "+remotePeers.size()+" remote peers.");
	/* terminate all remote peers that communicate through this gateway */
	for(RemotePeerController rpc : remotePeers.values()) {
	    rpc.terminate(c);
	}
    }
    
}
