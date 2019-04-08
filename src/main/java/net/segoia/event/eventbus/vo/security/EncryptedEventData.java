package net.segoia.event.eventbus.vo.security;

public class EncryptedEventData {
    /**
     * base 64 encoded and encrypted data
     */
    private String encryptedEvent;

    public EncryptedEventData(String encryptedEvent) {
	super();
	this.encryptedEvent = encryptedEvent;
    }

    public EncryptedEventData() {
	super();
    }

    public String getEncryptedEvent() {
	return encryptedEvent;
    }

    public void setEncryptedEvent(String encryptedEvent) {
	this.encryptedEvent = encryptedEvent;
    }

}
