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

import net.segoia.event.eventbus.peers.vo.session.SessionKeyPlainData;

public class IdentityLinkPrivateData {

    /**
     * The alias to identity this link by
     */
    private String alias;

    /**
     * The key used by the server to uniquely identify this link
     */
    private String idsLinkKey;

    /**
     * The id of the linking session. This can be used to verify the linked
     * nodes
     */
    private String linkingSessionId;

    /**
     * The session key used to encrypt events to/from partner
     */
    private SessionKeyPlainData sessionData;
    
    private LinkTrustLevel trustLevel;

    public IdentityLinkPrivateData() {
        super();
    }

    public IdentityLinkPrivateData(String alias) {
        super();
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIdsLinkKey() {
        return idsLinkKey;
    }

    public void setIdsLinkKey(String idsLinkKey) {
        this.idsLinkKey = idsLinkKey;
    }

    public String getLinkingSessionId() {
        return linkingSessionId;
    }

    public void setLinkingSessionId(String linkingSessionId) {
        this.linkingSessionId = linkingSessionId;
    }

    public SessionKeyPlainData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionKeyPlainData sessionData) {
        this.sessionData = sessionData;
    }

    public LinkTrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(LinkTrustLevel trustLevel) {
        this.trustLevel = trustLevel;
    }

}
