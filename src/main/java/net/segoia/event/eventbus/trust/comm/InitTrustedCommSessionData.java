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

public class InitTrustedCommSessionData {
    private String linkId;
    
    /**
     * To be filled by the proxy node
     */
    private String sessionId;

    /**
     * The id of the service that will used this trusted session
     */
    private String serviceId;
    
    /**
     * The id of the key to be used for encryption
     */
    private String sessionKeyId;

    public InitTrustedCommSessionData() {
	super();
    }

    public InitTrustedCommSessionData(String linkId) {
	super();
	this.linkId = linkId;
    }

    public InitTrustedCommSessionData(String linkId, String serviceId) {
	super();
	this.linkId = linkId;
	this.serviceId = serviceId;
    }

    public InitTrustedCommSessionData(String linkId, String serviceId, String sessionKeyId) {
        this.linkId = linkId;
        this.serviceId = serviceId;
        this.sessionKeyId = sessionKeyId;
    }
    
    

    public String getLinkId() {
	return linkId;
    }

    public void setLinkId(String linkId) {
	this.linkId = linkId;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSessionKeyId() {
        return sessionKeyId;
    }

    public void setSessionKeyId(String sessionKeyId) {
        this.sessionKeyId = sessionKeyId;
    }
    
    

}
