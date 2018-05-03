package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.peers.vo.security.IssueIdentityRequest;

public interface IdentityBuilder<I extends NodeIdentity<?>> {
    I buildIdentity(IssueIdentityRequest request) throws IdentityException;
}
