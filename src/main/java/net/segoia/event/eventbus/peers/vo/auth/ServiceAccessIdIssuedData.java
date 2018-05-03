package net.segoia.event.eventbus.peers.vo.auth;

import java.util.List;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.vo.services.ServiceContract;

public class ServiceAccessIdIssuedData {
    private NodeIdentity<?> accessIdentity;
    private List<ServiceContract> servicePolicies;

    public ServiceAccessIdIssuedData(NodeIdentity<?> accessIdentity, List<ServiceContract> servicePolicies) {
	super();
	this.accessIdentity = accessIdentity;
	this.servicePolicies = servicePolicies;
    }

    public ServiceAccessIdIssuedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public NodeIdentity<?> getAccessIdentity() {
	return accessIdentity;
    }

    public void setAccessIdentity(NodeIdentity<?> accessIdentity) {
	this.accessIdentity = accessIdentity;
    }

    public List<ServiceContract> getServicePolicies() {
	return servicePolicies;
    }

    public void setServicePolicies(List<ServiceContract> servicePolicies) {
	this.servicePolicies = servicePolicies;
    }

}
