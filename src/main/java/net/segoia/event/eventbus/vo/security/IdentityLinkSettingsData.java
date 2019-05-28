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

public class IdentityLinkSettingsData {
    private String idsLinkKey;
    private LinkTrustLevel trustLevel;
    private IdentityLinkPublicData publicData;
    
    public IdentityLinkSettingsData(String idsLinkKey, LinkTrustLevel trustLevel, IdentityLinkPublicData publicData) {
	super();
	this.idsLinkKey = idsLinkKey;
	this.trustLevel = trustLevel;
	this.publicData = publicData;
    }

    public IdentityLinkSettingsData() {
	super();
    }

    public String getIdsLinkKey() {
        return idsLinkKey;
    }

    public void setIdsLinkKey(String idsLinkKey) {
        this.idsLinkKey = idsLinkKey;
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
