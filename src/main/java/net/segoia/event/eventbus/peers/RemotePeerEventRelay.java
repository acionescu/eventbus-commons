package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;

public class RemotePeerEventRelay extends EventRelay{
    /**
     * The peer manager that mediates communication with remote peer
     */
    private PeerManager proxyPeerManager;
    /**
     * The id key associated with the remote peer
     */
    private String remotePeerIdKey;
    
    private String channel;
    

    public RemotePeerEventRelay(String id, PeerManager proxyPeerManager, String remotePeerIdKey) {
	super(id);
	this.proxyPeerManager = proxyPeerManager;
	this.remotePeerIdKey = remotePeerIdKey;
	
	channel = proxyPeerManager.getPeerContext().getCommunicationChannel()+"-REMOTE";
    }




    @Override
    public void bind() {
	/* no need, we send events to the proxy peer manager */
    }




    @Override
    public String getChannel() {
	return channel;
    }




    @Override
    public void sendEvent(Event event) {
//	System.out.println("remote peer relay: set remote peer id key "+remotePeerIdKey+" on  event "+event.getEt());
	
	/* set the id on the event header so that the proxy knows where to forward the message */
	event.getHeader().setRemoteAgentId(remotePeerIdKey);
	/* delegate the sending to the proxy peer manager */
	proxyPeerManager.forwardToPeer(event);
    }




    @Override
    protected void cleanUp() {
	// TODO Auto-generated method stub
	
    }

}
