package net.segoia.event.eventbus.peers.vo.auth;

import java.util.List;

import net.segoia.event.eventbus.peers.vo.auth.id.IdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;

public class NodeAuth {
    /**
     * A node may define more identities for more identity types, in the preferred order
     */
    public List<? extends NodeIdentity<? extends IdentityType>> identities;

    public List<? extends NodeIdentity<? extends IdentityType>> getIdentities() {
	return identities;
    }

    public void setIdentities(List<? extends NodeIdentity<? extends IdentityType>> identities) {
	this.identities = identities;
    }

}
