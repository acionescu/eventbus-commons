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
import net.segoia.event.eventbus.vo.security.PublicKeyInfo;

public class SpkiNodeIdentity extends NodeIdentity<SpkiNodeIdentityType> {
    private String publicKey;

    public SpkiNodeIdentity(String publicKey, String algorithm, int keySize) {
	super(new SpkiNodeIdentityType(algorithm, keySize));
	this.publicKey = publicKey;
    }
    
    

    public SpkiNodeIdentity() {
	super(new SpkiNodeIdentityType());
    }



    public String getPublicKey() {
	return publicKey;
    }

    public void setPublicKey(String publicKey) {
	this.publicKey = publicKey;
    }

    public PublicKeyInfo getPublicKeyInfo() {
	SpkiNodeIdentityType type = getType();
	return new PublicKeyInfo(publicKey, new KeyDef(type.getAlgorithm(), type.getKeySize()));
    }
    
}
