package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.auth.NodeAuth;
import net.segoia.event.eventbus.peers.vo.auth.id.IdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;

public abstract class PrivateIdentityData<N extends NodeIdentity<? extends IdentityType>> implements PrivateIdentityManager{
    /**
     * position in the {@link NodeAuth} identities list
     */
    private int index;

    private N publicNodeIdentity;

    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public N getPublicNodeIdentity() {
	return publicNodeIdentity;
    }

    public void setPublicNodeIdentity(N publicNodeIdentity) {
	this.publicNodeIdentity = publicNodeIdentity;
    }

    @Override
    public String getType() {
	return getPublicNodeIdentity().getType().getType();
    }

}
