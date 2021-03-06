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
package net.segoia.event.eventbus.peers.vo.auth.id;

import net.segoia.event.eventbus.peers.vo.session.KeyDef;

public class SpkiFullNodeIdentity extends NodeIdentity<SpkiFullIdentityType> {
    private String pubKey;
    private String privateKey;
    /**
     * Requires the private key to be securely encrypted before storing to unsafe media <br>
     * True by default
     */
    private boolean encryptPkDuringStorageOn = true;

    public SpkiFullNodeIdentity(KeyDef keyDef) {
	super(new SpkiFullIdentityType(keyDef));
    }

    public SpkiFullNodeIdentity() {
	super(null);
    }

    public String getPubKey() {
	return pubKey;
    }

    public void setPubKey(String pubKey) {
	this.pubKey = pubKey;
    }

    public String getPrivateKey() {
	return privateKey;
    }

    public void setPrivateKey(String privateKey) {
	this.privateKey = privateKey;
    }

    public boolean isEncryptPkDuringStorageOn() {
	return encryptPkDuringStorageOn;
    }

    public void setEncryptPkDuringStorageOn(boolean encryptPkDuringStorageOn) {
	this.encryptPkDuringStorageOn = encryptPkDuringStorageOn;
    }

}
