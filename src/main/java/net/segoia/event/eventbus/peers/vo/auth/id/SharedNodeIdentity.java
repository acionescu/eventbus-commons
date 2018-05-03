package net.segoia.event.eventbus.peers.vo.auth.id;

public class SharedNodeIdentity extends NodeIdentity<SharedIdentityType> {
    private byte[] iv;
    private byte[] keyBytes;

    public SharedNodeIdentity(SharedIdentityType type, byte[] keyBytes, byte[] iv) {
	super(type);
	this.keyBytes = keyBytes;
	this.iv = iv;
    }

    public byte[] getIv() {
	return iv;
    }

    public byte[] getKeyBytes() {
	return keyBytes;
    }

    public void setKeyBytes(byte[] keyBytes) {
	this.keyBytes = keyBytes;
    }

    public void setIv(byte[] iv) {
	this.iv = iv;
    }

}
