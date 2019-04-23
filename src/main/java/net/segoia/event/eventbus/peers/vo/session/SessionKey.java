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

public class SessionKey {
    private String sessionId;
    private byte[] keyBytes;
    private KeyDef keyDef;
    private byte[] iv;

    public SessionKey(String sessionId, byte[] keyBytes, KeyDef keyDef) {
	super();
	this.sessionId = sessionId;
	this.keyBytes = keyBytes;
	this.keyDef = keyDef;
    }

    public SessionKey(String sessionId, byte[] keyBytes, KeyDef keyDef, byte[] iv) {
        this.sessionId = sessionId;
        this.keyBytes = keyBytes;
        this.keyDef = keyDef;
        this.iv = iv;
    }
    
    

    public byte[] getKeyBytes() {
	return keyBytes;
    }

    public void setKeyBytes(byte[] keyBytes) {
	this.keyBytes = keyBytes;
    }

    public KeyDef getKeyDef() {
	return keyDef;
    }

    public void setKeyDef(KeyDef keyDef) {
	this.keyDef = keyDef;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
    
}
