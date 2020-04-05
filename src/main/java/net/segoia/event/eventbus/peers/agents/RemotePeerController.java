package net.segoia.event.eventbus.peers.agents;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeersAgentContext;
import net.segoia.event.eventbus.peers.RemotePeerManager;

public class RemotePeerController extends PeersAgentController {
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
	
	context.logger().info("remote peer controller -> "+data.getPeerId()+" handle "+event.toJson());
	
	if(remotePeerManager != null) {
	    remotePeerManager.onPeerEvent(event);
	    event.setHandled();
	}
	
	
    }
}
