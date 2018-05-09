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
package net.segoia.event.eventbus.peers.security;

import net.segoia.event.eventbus.peers.vo.comm.EncryptWithPublicCommOperationDef;

public class DecryptWithPrivateOperationContext extends SpkiSpkiCommOperationContext<EncryptWithPublicCommOperationDef> {
    private DecryptOperationWorker privateDecryptWorker;

    public DecryptWithPrivateOperationContext(EncryptWithPublicCommOperationDef opDef, SpkiPrivateIdentityManager ourIdentity,
	    SpkiPublicIdentityManager peerIdentity) {
	super(opDef, ourIdentity, peerIdentity);
    }

    protected DecryptOperationWorker getPrivateDecryptWorker() throws Exception {
	if (privateDecryptWorker == null) {
	    privateDecryptWorker = getOurIdentity().buildPrivateDecryptWorker(getOpDef());
	}
	return privateDecryptWorker;
    }

    public byte[] decrypt(byte[] data) throws Exception {
	try {
	    return getPrivateDecryptWorker().decrypt(data);
	} catch (Throwable t) {
	    /* in case of error, force reset of the worker */
	    privateDecryptWorker = null;
	    throw t;
	}
    }
    
    public byte[] decrypt(OperationData opData) throws Exception {
	return decrypt(serialize(opData));
    }

}
