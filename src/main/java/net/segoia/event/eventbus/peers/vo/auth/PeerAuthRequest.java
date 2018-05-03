package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;

public class PeerAuthRequest {
    private NodeInfo nodeInfo;
    /**
     * Can optionally propose a communication protocol
     */
    private CommunicationProtocol communicationProtocol;

    public PeerAuthRequest(NodeInfo nodeInfo) {
	super();
	this.nodeInfo = nodeInfo;

    }

    public PeerAuthRequest() {
	super();
    }

    public NodeInfo getNodeInfo() {
	return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
	this.nodeInfo = nodeInfo;
    }

    public CommunicationProtocol getCommunicationProtocol() {
	return communicationProtocol;
    }

    public void setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
	this.communicationProtocol = communicationProtocol;
    }

}
