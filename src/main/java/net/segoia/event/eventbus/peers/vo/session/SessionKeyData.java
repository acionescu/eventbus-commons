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
package net.segoia.event.eventbus.peers.vo.session;

import net.segoia.event.eventbus.peers.vo.security.ChannelSessionPolicy;

public class SessionKeyData {
    /**
     * The base 64 encoded session key, after it was processed by the operations specified in the
     * {@link ChannelSessionPolicy} <br>
     * The receiver will have to apply the inverse operations, and decode the result in order to obtain the session key
     */
    private String sessionToken;

    /**
     * The signature of the sessionToken
     */
    private String sessionTokenSignature;
    
    /**
     * Encoded key iv
     */
    private String keyIv;

    /**
     * Defines the session key type
     */
    private KeyDef keyDef;
    
    

    public SessionKeyData(String sessionToken, KeyDef keyDef) {
	super();
	this.sessionToken = sessionToken;
	this.keyDef = keyDef;
    }

    public SessionKeyData(String sessionToken, String sessionTokenSignature, KeyDef keyDef) {
	super();
	this.sessionToken = sessionToken;
	this.sessionTokenSignature = sessionTokenSignature;
	this.keyDef = keyDef;
    }

    public String getSessionToken() {
	return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
	this.sessionToken = sessionToken;
    }

    public KeyDef getKeyDef() {
	return keyDef;
    }

    public void setKeyDef(KeyDef keyDef) {
	this.keyDef = keyDef;
    }

    public String getSessionTokenSignature() {
	return sessionTokenSignature;
    }

    public void setSessionTokenSignature(String sessionTokenSignature) {
	this.sessionTokenSignature = sessionTokenSignature;
    }

    public String getKeyIv() {
        return keyIv;
    }

    public void setKeyIv(String keyIv) {
        this.keyIv = keyIv;
    }

}
