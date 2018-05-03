package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public interface IdentitiesRepository {
    void storeIdentityProfile(NodeIdentityProfile identityProfile);
    NodeIdentityProfile getIdentityProfile(String identityKey);
}
