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

import net.segoia.event.eventbus.peers.vo.comm.SignCommOperationDef;

public class SignatureInfo {

    /**
     * The sha256 digest of the public key
     */
    private String idKey;
    /* base64 encoded signature */
    private String signature;
    private SignCommOperationDef signatureDef;
    private PublicKeyInfo keyInfo; 

    public SignatureInfo(String idKey, String signature, SignCommOperationDef signatureDef) {
        super();
        this.idKey = idKey;
        this.signature = signature;
        this.signatureDef = signatureDef;
    }

    public SignatureInfo() {
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public SignCommOperationDef getSignatureDef() {
        return signatureDef;
    }

    public void setSignatureDef(SignCommOperationDef signatureDef) {
        this.signatureDef = signatureDef;
    }

    public PublicKeyInfo getKeyInfo() {
        return keyInfo;
    }

    public void setKeyInfo(PublicKeyInfo keyInfo) {
        this.keyInfo = keyInfo;
    }

}
