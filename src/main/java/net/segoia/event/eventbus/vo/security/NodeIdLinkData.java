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

public class NodeIdLinkData {
    private String ownerIdKey;
    private LinkTrustLevel trustLevel;
    private IdentityLinkPublicData publicData;

    public NodeIdLinkData(String ownerIdKey, LinkTrustLevel trustLevel) {
	super();
	this.ownerIdKey = ownerIdKey;
	this.trustLevel = trustLevel;
    }

    public NodeIdLinkData(String ownerIdKey, LinkTrustLevel trustLevel, IdentityLinkPublicData publicData) {
	super();
	this.ownerIdKey = ownerIdKey;
	this.trustLevel = trustLevel;
	this.publicData = publicData;
    }

    public NodeIdLinkData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public String getOwnerIdKey() {
	return ownerIdKey;
    }

    public void setOwnerIdKey(String ownerIdKey) {
	this.ownerIdKey = ownerIdKey;
    }

    public LinkTrustLevel getTrustLevel() {
	return trustLevel;
    }

    public void setTrustLevel(LinkTrustLevel trustLevel) {
	this.trustLevel = trustLevel;
    }

    public IdentityLinkPublicData getPublicData() {
	return publicData;
    }

    public void setPublicData(IdentityLinkPublicData publicData) {
	this.publicData = publicData;
    }

}
