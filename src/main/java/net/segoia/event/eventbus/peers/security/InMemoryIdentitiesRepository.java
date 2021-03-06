/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.event.eventbus.peers.security;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.peers.core.IdentitiesRepository;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.peers.vo.auth.id.SpkiNodeIdentity;
import net.segoia.event.eventbus.vo.security.IdsLinkData;
import net.segoia.event.eventbus.vo.security.NodeIdLinkData;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class InMemoryIdentitiesRepository implements IdentitiesRepository {
    private Map<Class<?>, IssuedIdentityManager<?>> identityManagers;
    private Map<String, NodeIdentityProfile> identityProfiles=new HashMap<>();

    public InMemoryIdentitiesRepository() {
	identityManagers = new HashMap<>();
	identityManagers.put(SpkiNodeIdentity.class, new IssuedIdentityManager<SpkiNodeIdentity>() {
	    private Map<String, NodeIdentityCertificationInfo> issuedIdentities = new HashMap<>();

	    @Override
	    public boolean verify(SpkiNodeIdentity identity) {
		String publicKey = identity.getPublicKey();
		return issuedIdentities.get(publicKey) != null;
	    }

	    @Override
	    public void store(SpkiNodeIdentity identity) {
		issuedIdentities.put(identity.getPublicKey(), new NodeIdentityCertificationInfo());
	    }
	});
    }

    public interface IssuedIdentityManager<I extends NodeIdentity<?>> {
	boolean verify(I identity);

	void store(I identity);
    }

    @Override
    public void storeIdentityProfile(NodeIdentityProfile identityProfile) {
	identityProfiles.put(identityProfile.getIdentityKey(), identityProfile);
    }

    @Override
    public NodeIdentityProfile getIdentityProfile(String identityKey) {
	return identityProfiles.get(identityKey);
    }

    @Override
    public void storeIdsLinkData(IdsLinkData data) {
	throw new RuntimeException("Store ids link not supported");
	
    }

    @Override
    public IdsLinkData getIdsLinkData(String idsLinkKey) {
	throw new RuntimeException("Get ids link data not supported");
    }

    @Override
    public void removeIdsLinkData(String idsLinkKey) {
	throw new RuntimeException("Remove ids link data not supported");
    }

    @Override
    public Map<String, NodeIdLinkData> getAllLinksForIdKey(String idKey) {
	throw new RuntimeException("Not supported");
    }

//    @Override
//    public boolean verify(NodeIdentity<?> identity) {
//	IssuedIdentityManager im = identityManagers.get(identity.getClass());
//	if (im != null) {
//	    return im.verify(identity);
//	}
//	return true;
//    }
//
//    @Override
//    public void storeIdentity(NodeIdentity<?> identity) {
//	IssuedIdentityManager im = identityManagers.get(identity.getClass());
//	if (im != null) {
//	    im.store(identity);
//	} else {
//	    throw new RuntimeException("Node identity manager found for type " + identity.getClass());
//	}
//    }
}
