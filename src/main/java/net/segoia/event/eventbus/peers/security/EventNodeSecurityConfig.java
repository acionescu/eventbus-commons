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

import java.util.List;

import net.segoia.event.eventbus.peers.core.IdentitiesManager;
import net.segoia.event.eventbus.peers.vo.auth.NodeAuth;
import net.segoia.event.eventbus.peers.vo.security.EventNodeSecurityPolicy;

public class EventNodeSecurityConfig {
    private NodeAuth nodeAuth;
    private EventNodeSecurityPolicy securityPolicy;

    /**
     * Private identity data loaders
     */
    private List<PrivateIdentityDataLoader<?>> identityLoaders;

    private IdentitiesManager identitiesManager;

    public NodeAuth getNodeAuth() {
	return nodeAuth;
    }

    public void setNodeAuth(NodeAuth nodeAuth) {
	this.nodeAuth = nodeAuth;
    }

    public EventNodeSecurityPolicy getSecurityPolicy() {
	return securityPolicy;
    }

    public void setSecurityPolicy(EventNodeSecurityPolicy securityPolicy) {
	this.securityPolicy = securityPolicy;
    }

    public List<PrivateIdentityDataLoader<?>> getIdentityLoaders() {
	return identityLoaders;
    }

    public void setIdentityLoaders(List<PrivateIdentityDataLoader<?>> identityLoaders) {
	this.identityLoaders = identityLoaders;
    }

    public IdentitiesManager getIdentitiesManager() {
	return identitiesManager;
    }

    public void setIdentitiesManager(IdentitiesManager identitiesManager) {
	this.identitiesManager = identitiesManager;
    }

}
