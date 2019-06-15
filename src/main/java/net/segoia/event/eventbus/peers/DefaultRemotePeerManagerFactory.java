package net.segoia.event.eventbus.peers;

public class DefaultRemotePeerManagerFactory implements PeerManagerFactory{

    @Override
    public PeerManager buildPeerManager(PeerContext peerContext) {
	return new RemotePeerManager((RemotePeerContext)peerContext);
    }

}
