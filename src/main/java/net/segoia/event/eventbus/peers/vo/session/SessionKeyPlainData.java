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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.segoia.event.eventbus.peers.vo.session;

/**
 *
 * @author adi
 */
public class SessionKeyPlainData {
    private String sessionId;
    private String keyData;
    private KeyDef keyDef;
    private String ivData;

    public SessionKeyPlainData() {
    }

    public SessionKeyPlainData(String sessionId, String keyData, KeyDef keyDef, String ivData) {
        this.sessionId = sessionId;
        this.keyData = keyData;
        this.keyDef = keyDef;
        this.ivData = ivData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getKeyData() {
        return keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }

    public KeyDef getKeyDef() {
        return keyDef;
    }

    public void setKeyDef(KeyDef keyDef) {
        this.keyDef = keyDef;
    }

    public String getIvData() {
        return ivData;
    }

    public void setIvData(String ivData) {
        this.ivData = ivData;
    }

    
    
}
