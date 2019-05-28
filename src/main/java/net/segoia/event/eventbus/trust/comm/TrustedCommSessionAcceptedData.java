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
package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionAcceptedData {

    /**
     * The session id allocated by the server for this connection
     */
    private String sessionId;
    private String linkId;
    /**
     * The id of the key used to encrypt data, if one was sent by the initiator
     * and accepted by this party
     */
    private String sessionKeyId;

    public TrustedCommSessionAcceptedData() {
        super();
    }

    public TrustedCommSessionAcceptedData(String sessionId) {
        super();
        this.sessionId = sessionId;
    }

    public TrustedCommSessionAcceptedData(String sessionId, String linkId) {
        super();
        this.sessionId = sessionId;
        this.linkId = linkId;
    }

    public TrustedCommSessionAcceptedData(String sessionId, String linkId, String sessionKeyId) {
        this.sessionId = sessionId;
        this.linkId = linkId;
        this.sessionKeyId = sessionKeyId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getSessionKeyId() {
        return sessionKeyId;
    }

    public void setSessionKeyId(String sessionKeyId) {
        this.sessionKeyId = sessionKeyId;
    }

}
