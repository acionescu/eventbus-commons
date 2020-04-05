package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.agents.RemotePeerDataContext;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;

public class RemotePeerManager extends PeerManager {
    private RemotePeerDataContext remoteContext;

    public RemotePeerManager(RemotePeerDataContext remoteContext) {
	super(remoteContext, false);
	this.remoteContext = remoteContext;
	init(remoteContext);
    }

    @Override
    protected void init(PeerContext peerContext) {
	
	setPeerType(remoteContext.getGatewayPeer().getPeerType());
	setPeerId(peerContext.getPeerId());
	System.out.println("Initializing RemotePeerManager with id "+getPeerId());
    }

    @Override
    public void start() {
	setAcceptedState(REMOTE_ACCEPTED);
	onReady();
    }

    @Override
    public void onReady() {
	goToState(getAcceptedState());
    }

    public static PeerManagerState REMOTE_ACCEPTED = new PeerManagerState() {

	@Override
	public <E extends Event> boolean handleEventFromPeer(PeerEventContext<E> ec) {
	    E event = ec.getEvent();
	    ec.getPeerManager().postEvent(event);
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
