package net.segoia.event.eventbus.peers.agents;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.RemotePeerManager;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;

public class RemotePeerDataContext extends PeerContext {
    public static final String PEER_PATH_SEPARATOR = ":";
    private String remotePeerId;
    private PeerManager gatewayPeer;
    /**
     * Create a full remote peer path composed from gateway peer id and remote peer id. This should be unique on the
     * local node and can act as a local remote peer id
     */
    private String fullRemotePeerPath;

    /**
     * The peer manager created to handle this remote peer
     */
    private RemotePeerManager remotePeerManager;
    
    private String commChannel;

    public RemotePeerDataContext(PeerEventContext<PeerAcceptedEvent> pc) {
	super(null, null);
	gatewayPeer = pc.getPeerManager();
	remotePeerId = pc.getEvent().getData().getPeerId();
	fullRemotePeerPath = gatewayPeer.getPeerId() + PEER_PATH_SEPARATOR + remotePeerId;

	/* if not overwritten, use this as local remote peer id */
	setPeerId(fullRemotePeerPath);
	setRemoteAgent(true);
	
	commChannel = gatewayPeer.getPeerContext().getCommunicationChannel();
    }

    @Override
    public void sendEventToPeer(Event event) {
	/* instruct gateway to forward the event to the remote peer */
	event.to(remotePeerId);
	getNodeContext().getLogger().info("forwarding to remote peer "+event.toJson());
	gatewayPeer.forwardToPeer(event);
    }

    public String getRemotePeerId() {
	return remotePeerId;
    }

    public PeerManager getGatewayPeer() {
	return gatewayPeer;
    }

    public String getFullRemotePeerPath() {
	return fullRemotePeerPath;
    }

    public String getGatewayPeerId() {
	return gatewayPeer.getPeerId();
    }

    public RemotePeerManager getRemotePeerManager() {
	return remotePeerManager;
    }

    public void setRemotePeerManager(RemotePeerManager remotePeerManager) {
	this.remotePeerManager = remotePeerManager;
    }

    @Override
    public String getCommunicationChannel() {
	return commChannel;
    }

}
