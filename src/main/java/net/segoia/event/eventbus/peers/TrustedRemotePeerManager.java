package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventHandler;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.IdentityException;
import net.segoia.event.eventbus.peers.core.PeerCommErrorEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerSessionException;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.security.CryptoHelper;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.vo.PeerErrorData;
import net.segoia.event.eventbus.peers.vo.session.SessionInfo;
import net.segoia.event.eventbus.peers.vo.session.SessionKey;
import net.segoia.event.eventbus.peers.vo.session.SessionKeyPlainData;
import net.segoia.event.eventbus.trust.comm.StartTrustedCommSessionData;
import net.segoia.event.eventbus.trust.comm.StartTrustedCommSessionEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionAcceptedData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionAcceptedEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionClosedData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionClosedEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionEstablishedData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionEstablishedEvent;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionReadyData;
import net.segoia.event.eventbus.trust.comm.TrustedCommSessionReadyEvent;
import net.segoia.event.eventbus.vo.security.IdentityLinkFullData;
import net.segoia.event.eventbus.vo.security.IdentityLinkPrivateData;

public class TrustedRemotePeerManager extends PeerManager {

    private EventHandler handler;
    private IdentityLinkFullData trustData;

    public TrustedRemotePeerManager(PeerContext peerContext, EventHandler handler, IdentityLinkFullData trustData) {
	super(peerContext);
	this.handler = handler;
	this.trustData = trustData;
    }

    public TrustedRemotePeerManager(String peerId, EventTransceiver transceiver) {
	super(peerId, transceiver);
    }

    @Override
    public void generateNewSession() throws PeerSessionException {

	IdentityLinkPrivateData privateData = trustData.getPrivateData();
	SessionKeyPlainData sessionData = privateData.getSessionData();

	/* check if we already have a session encryption key generated for this link */
	if (sessionData != null) {
	    /* if we already have a session key, then build a session meneger from it */
	    EventNodeSecurityManager securityManager = getNodeContext().getSecurityManager();
	    try {
		securityManager.buildSessionFromPlainData(getPeerContext(), sessionData);
	    } catch (CommOperationException e) {
		throw new PeerSessionException("Failed to generate session from known session info", e);
	    }
	} else {
	    /* if we don't have a session key already, generate one */

	    /* we'll also neet the session comm manager for this */
	    setUpSessionCommManager();
	    super.generateNewSession();
	}
    }

    @Override
    public void onProtocolConfirmed() throws PeerSessionException, CommOperationException {
	setUpPeerCommContext();

	generateNewSession();
	setUpDirectCommManager();

	/* send the session */
	startNewPeerSession();

	/* activate the transceiver implementing the protocol */
	setUpCommProtocolTransceiver();

    }

    @Override
    public void startNewPeerSession() throws CommOperationException {
	IdentityLinkPrivateData privateData = trustData.getPrivateData();
	SessionKeyPlainData prevSessionData = privateData.getSessionData();

	SessionInfo sessionInfo = null;

	if (prevSessionData != null) {
	    /* if we already had a session info, send only the sessionKeyId */
	    sessionInfo = new SessionInfo(prevSessionData.getSessionId(), null);

	} else {
	    /* generate session info */
	    sessionInfo = generateSessionInfo();

	    if (sessionInfo == null) {
		/* failed to create a session */
	    }

	    /* create session data form session key and save it */
	    SessionKeyPlainData newSessionData = getPlainSessionKeyData();
	    /* save new session data on the id link data */
	    trustData.getPrivateData().setSessionData(newSessionData);
	    getNodeContext().getSecurityManager().storeIdentityLinkFullData(trustData);

	}

	/* now we can send the session start event */
	sendNewSessionInfoToPeer(sessionInfo);
    }

    protected SessionKeyPlainData getPlainSessionKeyData() {
	SessionKey sessionKey = getPeerContext().getSessionKey();
	final CryptoHelper cryptoHelper = getNodeContext().getSecurityManager().getCryptoHelper();
	SessionKeyPlainData newSessionData = new SessionKeyPlainData(sessionKey.getSessionId(),
		cryptoHelper.base64Encode(sessionKey.getKeyBytes()), sessionKey.getKeyDef(),
		cryptoHelper.base64Encode(sessionKey.getIv()));
	return newSessionData;
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
            
            registerPeerEventProcessor(TrustedCommSessionClosedEvent.class, (c) -> {
		/* if we get a session closed, delegate this further for cleanup */
		c.getPeerManager().postEvent(c.getEvent());
	    });
            
            registerPeerEventProcessor(TrustedCommSessionReadyEvent.class, (c)->{
        	handlCommSessionReady(c);
            });

	}
	
	private void handlCommSessionReady(PeerEventContext<TrustedCommSessionReadyEvent> c) {
	    TrustedRemotePeerManager peerManager = (TrustedRemotePeerManager) c.getPeerManager();
	    peerManager.onReady();
	}

	private void handleCommSessionAccepted(PeerEventContext<TrustedCommSessionAcceptedEvent> c) {
	    TrustedRemotePeerManager peerManager = (TrustedRemotePeerManager) c.getPeerManager();
            
            TrustedCommSessionAcceptedData data = c.getEvent().getData();
            
            String sessionKeyId = data.getSessionKeyId();
            final IdentityLinkPrivateData privateData = peerManager.trustData.getPrivateData();
            SessionKeyPlainData prevSessionData = privateData.getSessionData();
            
            /* if the peer did not confirm the session key we sent, the generate a new one */
            if(prevSessionData != null && (sessionKeyId == null || !sessionKeyId.equals(prevSessionData.getSessionId()))){
                privateData.setSessionData(null);
            }

	    try {
		/* start using the protocol before sending the session started event */
		peerManager.onProtocolConfirmed();
	    } catch (PeerSessionException ex) {
		peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(-1, ex.getMessage())));
		return;
	    } catch (CommOperationException ex) {
		peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(-1, ex.getMessage())));
		return;
	    } catch (IdentityException ex) {
		peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(-1, ex.getMessage())));
		return;
	    } catch (Throwable t) {
		peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(0, t.getMessage())));
		return;
	    }

	    
	}

    };

    private static final PeerManagerState WAIT_FOR_START_SESSION = new PeerManagerState() {

	@Override
	protected void registerPeerEventHandlers() {
	    registerPeerEventProcessor(StartTrustedCommSessionEvent.class, (c) -> {
		handleStartSession(c);
	    });

	    registerPeerEventProcessor(TrustedCommSessionClosedEvent.class, (c) -> {
		/* if we get a session closed, delegate this further for cleanup */
		c.getPeerManager().postEvent(c.getEvent());
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
	    TrustedRemotePeerManager peerManager = (TrustedRemotePeerManager) c.getPeerManager();

	    StartTrustedCommSessionData data = event.getData();

	    SessionInfo sessionInfo = data.getSessionInfo();

	    if (sessionInfo == null) {
		TrustedCommSessionClosedEvent closeEvent = new TrustedCommSessionClosedEvent(
			new TrustedCommSessionClosedData("session.info.missing"));
		peerManager.forwardToPeer(closeEvent);
		return;
	    }

	    String sessionId = sessionInfo.getSessionId();
	    if (sessionId == null) {
		TrustedCommSessionClosedEvent closeEvent = new TrustedCommSessionClosedEvent(
			new TrustedCommSessionClosedData("session.id.missing"));
		peerManager.forwardToPeer(closeEvent);
		return;
	    }

	    /* get session info we already used before, if the case */
	    IdentityLinkPrivateData privateData = peerManager.trustData.getPrivateData();
	    SessionKeyPlainData prevSessionData = privateData.getSessionData();

	    if (sessionInfo.getSessionData() == null) {

		if (prevSessionData == null) {
		    TrustedCommSessionClosedEvent closeEvent = new TrustedCommSessionClosedEvent(
			    new TrustedCommSessionClosedData("session.data.missing"));
		    peerManager.forwardToPeer(closeEvent);
		    return;
		}

		if (!sessionId.equals(prevSessionData.getSessionId())) {
		    TrustedCommSessionClosedEvent closeEvent = new TrustedCommSessionClosedEvent(
			    new TrustedCommSessionClosedData("session.id.mismatched"));
		    peerManager.forwardToPeer(closeEvent);
		    return;
		}

	    }

	    peerManager.setUpPeerCommContext();

	    PeerContext peerContext = peerManager.getPeerContext();
	    EventNodeSecurityManager securityManager = peerContext.getNodeContext().getSecurityManager();
	    try {
		if (prevSessionData == null || sessionInfo.getSessionData() != null) {

		    peerManager.setUpSessionCommManager();
		    securityManager.buildSessionFromSessionInfo(peerContext, sessionInfo);
		} else {
		    securityManager.buildSessionFromPlainData(peerContext, prevSessionData);
		}
	    } catch (CommOperationException e) {
		peerManager.handleError(e);
		return;
	    }
	    peerManager.setUpDirectCommManager();
	    peerManager.setUpCommProtocolTransceiver();

	    if (prevSessionData == null || !sessionId.equals(prevSessionData.getSessionId())) {
		/* if this is the first time we're using this session, or got other session data, save it */

		privateData.setSessionData(peerManager.getPlainSessionKeyData());
		securityManager.storeIdentityLinkFullData(peerManager.trustData);
	    }
	    /* notify peer we're ready */
	    peerManager.forwardToPeer(new TrustedCommSessionReadyEvent(new TrustedCommSessionReadyData()));
	    
	    /* start normal operation */
	    peerManager.onReady();
	}
    };

    private final PeerManagerState COMM_SESSION_ESTABLISHED = new PeerManagerState() {

	@Override
	protected void registerPeerEventHandlers() {
	    registerPeerEventProcessor((c) -> {
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
