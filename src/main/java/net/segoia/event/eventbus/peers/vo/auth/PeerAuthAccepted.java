package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;

public class PeerAuthAccepted {
    private CommunicationProtocol communicationProtocol;

    public CommunicationProtocol getCommunicationProtocol() {
	return communicationProtocol;
    }

    public void setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
	this.communicationProtocol = communicationProtocol;
    }

}
