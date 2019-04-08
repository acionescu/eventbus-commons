package net.segoia.event.eventbus.vo.security;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("PEER:EVENT:ENCRYPTED_EVENT")
public class EncryptedCustomEvent extends CustomEvent<EncryptedEventData> {

    public static final String ET = "PEER:EVENT:ENCRYPTED_EVENT";

    public EncryptedCustomEvent(EncryptedEventData data) {
	super(ET, data);
    }

    public EncryptedCustomEvent() {
	super(ET);
    }

    @Override
    public EncryptedEventData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(EncryptedEventData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
