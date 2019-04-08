package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.peers.vo.session.SessionInfo;

public class StartTrustedCommSessionData {
    private SessionInfo sessionInfo;

    public StartTrustedCommSessionData(SessionInfo sessionInfo) {
	super();
	this.sessionInfo = sessionInfo;
    }

    public StartTrustedCommSessionData() {
	super();
    }

    public SessionInfo getSessionInfo() {
	return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
	this.sessionInfo = sessionInfo;
    }

}
