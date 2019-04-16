package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:SESSION_ESTABLISHED")
public class TrustedCommSessionEstablishedEvent extends CustomEvent<TrustedCommSessionEstablishedData> {
    public static final String ET = "TRUST:COMM:SESSION_ESTABLISHED";

    public TrustedCommSessionEstablishedEvent(TrustedCommSessionEstablishedData data) {
	super(ET, data);
    }

    public TrustedCommSessionEstablishedEvent() {
	super(ET);
    }

}
