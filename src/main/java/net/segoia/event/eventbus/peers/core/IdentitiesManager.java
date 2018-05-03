package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.peers.vo.security.IssueIdentityRequest;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public interface IdentitiesManager {
    NodeIdentity<?> issueIdentity(IssueIdentityRequest request);
    void storeIdentityProfile(NodeIdentityProfile identityProfile);
    NodeIdentityProfile getIdentityProfile(String identityKey);
}
