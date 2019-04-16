package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventHandler;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.peers.vo.session.SessionInfo;
import net.segoia.event.eventbus.trust.comm.StartTrustedCommSessionData;
import net.segoia.event.eventbus.trust.comm.StartTrustedCommSessionEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionAcceptedData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionAcceptedEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionEstablishedData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionEstablishedEvent;

public class TrustedRemotePeerManager extends PeerManager {
    private EventHandler handler;

    public TrustedRemotePeerManager(PeerContext peerContext, EventHandler handler) {
	super(peerContext);
	this.handler = handler;
    }

    public TrustedRemotePeerManager(String peerId, EventTransceiver transceiver) {
	super(peerId, transceiver);
    }

    @Override
    protected void sendNewSessionInfoToPeer(SessionInfo sessionInfo) {
	forwardToPeer(new StartTrustedCommSessionEvent(new StartTrustedCommSessionData(sessionInfo)));
    }

    protected void startInClientMode() {
	if (getAcceptedState() == null) {
	    setAcceptedState(COMM_SESSION_ESTABLISHED);
	}
	goToState(WAIT_FOR_START_SESSION);

    }

    protected void startInServerMode() {

	if (getAcceptedState() == null) {
	    setAcceptedState(COMM_SESSION_ESTABLISHED);
	}
	goToState(INIT_COMM_SESSION);

    }

    @Override
    public void postEvent(Event event) {
	handler.handleEvent(new EventContext(event));
    }

    @Override
    public void onReady() {
	goToState(getAcceptedState());
	postEvent(new TrustedCommSessionEstablishedEvent(new TrustedCommSessionEstablishedData()));
    }

    private static final PeerManagerState INIT_COMM_SESSION = new PeerManagerState() {

	@Override
	public void onEnterState(PeerManager peerManager) {
	    peerManager.getPeerContext().getRelay().start();
	}

	@Override
	public void onExitState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}

	@Override
	protected void registerLocalEventHandlers() {
	    // TODO Auto-generated method stub

	}

	@Override
	protected void registerPeerEventHandlers() {
	    registerPeerEventProcessor(TrustedCommSessionAcceptedEvent.class, (c) -> {
		handleCommSessionAccepted(c);
	    });

	}

	private void handleCommSessionAccepted(PeerEventContext<TrustedCommSessionAcceptedEvent> c) {
	    PeerManager peerManager = c.getPeerManager();

	    /* start using the protocol before sending the session started event */
	    peerManager.onProtocolConfirmed();

	    peerManager.onReady();
	}

    };

    private static final PeerManagerState WAIT_FOR_START_SESSION = new PeerManagerState() {

	@Override
	protected void registerPeerEventHandlers() {
	    registerPeerEventProcessor(StartTrustedCommSessionEvent.class, (c) -> {
		handleStartSession(c);
	    });

	}

	@Override
	protected void registerLocalEventHandlers() {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onExitState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onEnterState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}

	private void handleStartSession(PeerEventContext<StartTrustedCommSessionEvent> c) {
	    StartTrustedCommSessionEvent event = c.getEvent();
	    PeerManager peerManager = c.getPeerManager();

	    StartTrustedCommSessionData data = event.getData();

	    SessionInfo sessionInfo = data.getSessionInfo();

	    peerManager.setUpSessionCommManager();

	    PeerContext peerContext = peerManager.getPeerContext();
	    EventNodeSecurityManager securityManager = peerContext.getNodeContext().getSecurityManager();
	    try {

		securityManager.buildSessionFromSessionInfo(peerContext, sessionInfo);
	    } catch (CommOperationException e) {
		peerManager.handleError(e);
		return;
	    }
	    peerManager.setUpDirectCommManager();
	    peerManager.setUpCommProtocolTransceiver();
	    peerManager.onReady();
	}
    };

    private final PeerManagerState COMM_SESSION_ESTABLISHED = new PeerManagerState() {

	@Override
	protected void registerPeerEventHandlers() {
	           registerPeerEventProcessor((c)->{
                       PeerManager peerManager = c.getPeerManager();
                       /* forward this event to the specified handler */
                       peerManager.postEvent(c.getEvent());
                   });

	}

	@Override
	protected void registerLocalEventHandlers() {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onExitState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onEnterState(PeerManager peerManager) {
	    // TODO Auto-generated method stub

	}
    };

}
