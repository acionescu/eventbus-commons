package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;

public class RemotePeerManager extends PeerManager{


    public RemotePeerManager(RemotePeerContext peerContext) {
	super(peerContext);
	setAcceptedState(ACCEPTED);
	
    }

    @Override
    protected EventRelay createEventRelay(PeerContext peerContext) {
	RemotePeerContext rpc = (RemotePeerContext)peerContext;
	
	return new RemotePeerEventRelay(rpc.getPeerId(), rpc.getProxyPeerManager(), rpc.getRemotePeerIdKey());
    }

    @Override
    protected String getPeerTypeFromContext(PeerContext peerContext) {
	return "REMOTE";
    }
    
    
    
    @Override
    public void onReady() {
	goToState(getAcceptedState());
    }


    
    

    @Override
    public void handleEventFromPeer(Event event) {
	/* make this peer as the source of the event */
	event.getHeader().setFrom(getPeerId());
	super.handleEventFromPeer(event);
    }





    /* create an accepted state */
    public static PeerManagerState ACCEPTED = new PeerManagerState() {

	@Override
	public <E extends Event> boolean handleEventFromPeer(PeerEventContext<E> ec) {
	    ec.getPeerManager().postEvent(ec.getEvent());
	    return true;
	}

	@Override
	protected void registerPeerEventHandlers() {
	    // TODO Auto-generated method stub

	}

	@Override
	protected void registerLocalEventHandlers() {

	}

	@Override
	public void onExitState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onEnterState(PeerManager peerManager) {

	}
    };

}
