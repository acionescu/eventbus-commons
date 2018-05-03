package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.RequestRejectReason;

public class ServiceAccessIdRequestRejectedReason extends RequestRejectReason<Object>{

    public ServiceAccessIdRequestRejectedReason(String message) {
	super(message);
    }

}
