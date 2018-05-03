package net.segoia.event.eventbus.peers.vo.auth.id;

import net.segoia.event.eventbus.peers.vo.session.KeyDef;

public class SpkiFullNodeIdentity extends NodeIdentity<SpkiFullIdentityType> {
    private String pubKey;
    private String privateKey;

    public SpkiFullNodeIdentity(KeyDef keyDef) {
	super(new SpkiFullIdentityType(keyDef));
    }

    public String getPubKey() {
	return pubKey;
    }

    public void setPubKey(String pubKey) {
	this.pubKey = pubKey;
    }

    public String getPrivateKey() {
	return privateKey;
    }

    public void setPrivateKey(String privateKey) {
	this.privateKey = privateKey;
    }
}
