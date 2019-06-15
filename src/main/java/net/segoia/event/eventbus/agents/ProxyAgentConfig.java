package net.segoia.event.eventbus.agents;

import net.segoia.event.conditions.Condition;
import net.segoia.event.eventbus.peers.core.EventTransceiver;

public class ProxyAgentConfig {
    /**
     * A string to identify the remote peer node
     */
    private String serviceNodeId;

    /**
     * The transceiver to use to connect to the service node
     */
    private EventTransceiver serviceNodeTransceiver;
    
    /**
     * The condition to distinguish client events
     */
    private Condition clientsCondition;

    public ProxyAgentConfig() {
	super();
    }

    public ProxyAgentConfig(String serviceNodeId, EventTransceiver serviceNodeTransceiver) {
	super();
	this.serviceNodeId = serviceNodeId;
	this.serviceNodeTransceiver = serviceNodeTransceiver;
    }

    public String getServiceNodeId() {
	return serviceNodeId;
    }

    public void setServiceNodeId(String serviceNodeId) {
	this.serviceNodeId = serviceNodeId;
    }

    public EventTransceiver getServiceNodeTransceiver() {
	return serviceNodeTransceiver;
    }

    public void setServiceNodeTransceiver(EventTransceiver serviceNodeTransceiver) {
	this.serviceNodeTransceiver = serviceNodeTransceiver;
    }

    public Condition getClientsCondition() {
        return clientsCondition;
    }

    public void setClientsCondition(Condition clientsCondition) {
        this.clientsCondition = clientsCondition;
    }
    
    

}
