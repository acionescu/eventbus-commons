package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.auth.id.IdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;

public interface PublicIdentityManagerFactory<N extends NodeIdentity<? extends IdentityType>> {
    
    PublicIdentityManager build(N nodeIdentity);
}
