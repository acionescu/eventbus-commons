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
package net.segoia.event.eventbus.vo.security;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class DataAuthLevel {
    private int authLevel = -1;

    /**
     * Will be true only if it has been authorized by the root identity
     */
    private boolean fullyAuthorized;

    /**
     * The identity used to authorize this data, if the case
     */
    private NodeIdentityProfile authIdProfile;

    public DataAuthLevel() {
	super();
    }

    public DataAuthLevel(int authLevel) {
	super();
	this.authLevel = authLevel;
    }

    public DataAuthLevel(int authLevel, boolean fullyAuthorized) {
	super();
	this.authLevel = authLevel;
	this.fullyAuthorized = fullyAuthorized;
    }

    public DataAuthLevel(int authLevel, boolean fullyAuthorized, NodeIdentityProfile authIdProfile) {
	super();
	this.authLevel = authLevel;
	this.fullyAuthorized = fullyAuthorized;
	this.authIdProfile = authIdProfile;
    }

    public int getAuthLevel() {
	return authLevel;
    }

    public void setAuthLevel(int authLevel) {
	this.authLevel = authLevel;
    }

    public boolean isFullyAuthorized() {
	return fullyAuthorized;
    }

    public NodeIdentityProfile getAuthIdProfile() {
	return authIdProfile;
    }

    @Override
    public String toString() {
	return "DataAuthLevel [authLevel=" + authLevel + ", fullyAuthorized=" + fullyAuthorized + "]";
    }

}
