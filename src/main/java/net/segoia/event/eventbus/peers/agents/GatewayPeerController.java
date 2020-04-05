package net.segoia.event.eventbus.peers.agents;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.PeersAgentContext;
import net.segoia.event.eventbus.peers.events.NewPeerEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.vo.PeerInfo;

public class GatewayPeerController extends PeersAgentController{
    private PeerManager gatewayPeerManager;
    /**
     * Remote peers handled by this gateway
     */
    private Map<String, RemotePeerController> remotePeers=new HashMap<>();
    

    public GatewayPeerController(PeersAgentContext context, PeerManager gatewayPeerManager) {
	super(context,false);
	this.gatewayPeerManager = gatewayPeerManager;
	init();
    }

    @Override
    protected void config() {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void initController() {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void registerHandlers() {
	// TODO Auto-generated method stub
	
    }
    
    public void handleRemotePeerEvent(PeerEventContext<Event> c) {
	Event event = c.getEvent();
	String remotePeerId = event.from();
	RemotePeerController remotePeerController = remotePeers.get(remotePeerId);
	if(remotePeerController != null) {
	    context.logger().info("Send event "+event.getEt()+" to remote peer controller "+remotePeerId);
	    remotePeerController.handleRemotePeerEvent(c);
	}
    }

    public void addNewRemotePeer(PeerEventContext<PeerAcceptedEvent> c) {
	PeerAcceptedEvent event = c.getEvent();
	PeerInfo peerInfo = event.getData();
	String remotePeerId = peerInfo.getPeerId();
	
	if(!remotePeers.containsKey(remotePeerId)) {
	    RemotePeerDataContext remotePeerDataContext = new RemotePeerDataContext(c);
	    RemotePeerController remotePeerController = new RemotePeerController(context, remotePeerDataContext);
	    remotePeers.put(remotePeerId, remotePeerController);
	    context.logger().info("Created remote peer controller for "+ remotePeerDataContext.getFullRemotePeerPath());
	}
	
    }
    
}
