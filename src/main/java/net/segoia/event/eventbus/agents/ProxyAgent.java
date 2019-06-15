package net.segoia.event.eventbus.agents;

import net.segoia.event.conditions.AndCondition;
import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.CustomPeerIdCondition;
import net.segoia.event.conditions.LooseEventMatchCondition;
import net.segoia.event.conditions.NotCondition;
import net.segoia.event.conditions.OrCondition;
import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventHeader;
import net.segoia.event.eventbus.FilteringEventBus;
import net.segoia.event.eventbus.peers.GlobalEventNodeAgent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.peers.vo.bind.ConnectToPeerRequest;

public class ProxyAgent extends GlobalEventNodeAgent {
    private ProxyAgentConfig config;

    private FilteringEventBus serviceBus;
    private FilteringEventBus clientsBus;

    /**
     * The peer id of the service node
     */
    private String servicePeerId;

    public ProxyAgent() {
	super();
    }

    
    
    public ProxyAgent(ProxyAgentConfig config) {
	super();
	this.config = config;
    }



    @Override
    protected void agentInit() {

	/* connect to the service node */
	connectToServiceNode();
    }

    @Override
    public void terminate() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void config() {
	String serviceNodeId = config.getServiceNodeId();

	/* spawn a separate bus to handle events coming form the service node */
	CustomPeerIdCondition servicePeerCond = new CustomPeerIdCondition(serviceNodeId + "-cond", serviceNodeId);
	serviceBus = context.getEventBusForCondition(servicePeerCond);

	Condition clientsCondition = config.getClientsCondition();
	if (clientsCondition == null) {
	    /*
	     * if no client condition is specified, then consider clients all other peers that except the service peer
	     */
	    clientsCondition = new NotCondition(servicePeerCond);
	}
	
	Condition notPeerConnectionCondition=new NotCondition(new OrCondition(LooseEventMatchCondition.build("PEER"),
		LooseEventMatchCondition.buildWithCategory("PEER")));
	
	Condition allowedClientForwardingCondition = new AndCondition(clientsCondition, notPeerConnectionCondition);

	/* spawn an extra bus to handle events from clients */
	clientsBus = context.getEventBusForCondition(allowedClientForwardingCondition);

    }

    @Override
    protected void registerHandlers() {
	serviceBus.addEventHandler(PeerAcceptedEvent.class, (c) -> {
	    handleServiceNodeAccepted(c);
	});

	serviceBus.addEventHandler(PeerLeftEvent.class, (c) -> {
	    onServiceNodeConnectionLost(c);
	});

	serviceBus.addEventHandler((c) -> {
	    handleEventsFromServiceNode(c);
	});

	clientsBus.addEventHandler((c) -> {
	    handleEventsFromClientNode(c);
	});
    }

    protected void onServiceNodeConnectionLost(CustomEventContext<PeerLeftEvent> c) {
	// TODO: we need to set in place a smart reconnection mechanism

	/* for now, just try to reconnect */
	connectToServiceNode();
    }

    protected void connectToServiceNode() {
	context.registerToPeer(new ConnectToPeerRequest(config.getServiceNodeTransceiver(), config.getServiceNodeId()));
    }

    protected void handleEventsFromClientNode(CustomEventContext<Event> c) {
	Event event = c.getEvent();
	/* events coming on clientsBus are ment to be forwarded to the service node */

	EventHeader header = event.getHeader();
	String rootAgentId = header.getRootAgentId();
	/* set root client node id as remote agent id */
	header.setRemoteAgentId(rootAgentId);
	
//	System.out.println("proxy: fwd event "+event.getEt()+" for client "+rootAgentId);

	/* clear sensitive data form event */
	event.clearSensitiveData();

	/* forward to service node */
	context.forwardTo(event, servicePeerId);
    }

    protected void handleEventsFromServiceNode(CustomEventContext<Event> c) {
	Event event = c.getEvent();
	EventHeader header = event.getHeader();

	/* see if this event is a reply for a particular client */
	String remoteAgentId = header.getRemoteAgentId();

//	System.out.println("proxy: got event "+event.getEt()+" for client "+remoteAgentId);
	
	if (remoteAgentId == null) {
	    /* if this event wasn't intended for a client node, ignore */
	    return;
	}

	// TODO: validate event: see if the target peer is one of the clients for this service

	/* remove sensitive data for this event */
	event.clearSensitiveData();

	/* forward the event to the client */
	context.forwartToPeerWithIdKey(event, remoteAgentId);
    }

    protected void handleServiceNodeAccepted(CustomEventContext<PeerAcceptedEvent> c) {
	servicePeerId = c.getEvent().getData().getPeerId();
    }

}
