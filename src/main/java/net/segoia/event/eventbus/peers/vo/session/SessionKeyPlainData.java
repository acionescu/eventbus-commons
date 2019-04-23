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
