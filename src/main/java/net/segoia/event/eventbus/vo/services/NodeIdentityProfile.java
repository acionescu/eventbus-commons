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
package net.segoia.event.eventbus.vo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;

public class NodeIdentityProfile {
    private String identityKey;
    private NodeIdentity<?> identity;
    /**
     * service id
     */
    private Map<String, ServiceContract> serviceContracts;

    /**
     * Identity key for the parent identity
     */
    private String parentIdentityKey;

    private String rootIdentityKey;

    private List<String> childIdentityKeysList;

    private List<String> roles;

    private long lastAuthTs;

    private long lastUpdateTs;

    public NodeIdentityProfile(NodeIdentity<?> identity) {
	super();
	this.identity = identity;
    }

    public NodeIdentityProfile(String identityKey, NodeIdentity<?> identity) {
	super();
	this.identityKey = identityKey;
	this.identity = identity;
    }

    public NodeIdentityProfile() {
	super();
    }

    public NodeIdentity<?> getIdentity() {
	return identity;
    }

    public void setIdentity(NodeIdentity<?> identity) {
	this.identity = identity;
    }

    public Map<String, ServiceContract> getServiceContracts() {
	return serviceContracts;
    }

    public void setServiceContracts(Map<String, ServiceContract> serviceContracts) {
	this.serviceContracts = serviceContracts;
    }

    public String getIdentityKey() {
	return identityKey;
    }

    public void setIdentityKey(String identityKey) {
	this.identityKey = identityKey;
    }

    public String getParentIdentityKey() {
	return parentIdentityKey;
    }

    public void setParentIdentityKey(String parentIdentityKey) {
	this.parentIdentityKey = parentIdentityKey;
    }

    public List<String> getChildIdentityKeysList() {
	return childIdentityKeysList;
    }

    public void setChildIdentityKeysList(List<String> childIdentityKeysList) {
	this.childIdentityKeysList = childIdentityKeysList;
    }

    public void addChildIdentityKey(String childIdKey) {
	if (childIdentityKeysList == null) {
	    childIdentityKeysList = new ArrayList<>();
	}
	childIdentityKeysList.add(childIdKey);
    }

    public boolean areServicesAccessible(List<EventNodeServiceRef> servicesRefs) {
	for (EventNodeServiceRef sr : servicesRefs) {
	    if (!serviceContracts.containsKey(sr.toString())) {
		return false;
	    }
	}
	return true;
    }

    public List<String> getRoles() {
	return roles;
    }

    public void setRoles(List<String> roles) {
	this.roles = roles;
    }

    public void addRole(String roleId) {
	if (roles == null) {
	    roles = new ArrayList<>();
	}

	if (!roles.contains(roleId)) {
	    roles.add(roleId);
	}
    }

    public long getLastAuthTs() {
	return lastAuthTs;
    }

    public void setLastAuthTs(long lastAuthTs) {
	this.lastAuthTs = lastAuthTs;
    }

    public String getRootIdentityKey() {
	return rootIdentityKey;
    }

    public void setRootIdentityKey(String rootIdentityKey) {
	this.rootIdentityKey = rootIdentityKey;
    }

    public long getLastUpdateTs() {
	return lastUpdateTs;
    }

    public void setLastUpdateTs(long lastUpdateTs) {
	this.lastUpdateTs = lastUpdateTs;
    }

}
