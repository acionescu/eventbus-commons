package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class RemotePeerContext extends PeerContext {
    /**
     * The peer manager mediating communication with the remote node
     */
    private PeerManager proxyPeerManager;
    
    /**
     * The id key associated with the remote peer
     */
    private String remotePeerIdKey;

    public RemotePeerContext(String peerId, PeerManager proxyPeerManager, String remotePeerIdKey) {
	super(peerId, null);
	this.proxyPeerManager = proxyPeerManager;
	this.remotePeerIdKey=remotePeerIdKey;
	
    }
    
    @Override
    public NodeIdentityProfile getCurrentPeerIdentityProfile() {
	NodeIdentityProfile peerIdentityProfile = getPeerIdentityProfile();
	if (peerIdentityProfile == null) {
	    /* user remote peer id key as root id key for this peer context */
	    peerIdentityProfile = getNodeContext().getSecurityManager().getIdentityProfile(remotePeerIdKey);
	    setPeerIdentityProfile(peerIdentityProfile);
	}
	return peerIdentityProfile;
	
	
    }

    public PeerManager getProxyPeerManager() {
        return proxyPeerManager;
    }

    public String getRemotePeerIdKey() {
        return remotePeerIdKey;
    }

    
}
