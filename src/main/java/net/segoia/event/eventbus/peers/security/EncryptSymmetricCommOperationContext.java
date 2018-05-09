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

import net.segoia.event.eventbus.peers.vo.comm.EncryptSymmetricOperationDef;

public class EncryptSymmetricCommOperationContext extends SymmetricCommOperationContext<EncryptSymmetricOperationDef> {
    private EncryptOperationWorker opWorker;

    public EncryptSymmetricCommOperationContext(EncryptSymmetricOperationDef opDef,
	    SharedSecretIdentityManager identityManager) {
	super(opDef, identityManager);
    }

    public EncryptOperationWorker getOpWorker() throws Exception {
	if (opWorker == null) {
	    opWorker = getIdentityManager().buildEncryptWorker(getOpDef());
	}
	return opWorker;
    }

    public byte[] encrypt(byte[] data) throws Exception {
	return getOpWorker().encrypt(data);
    }
    
    public byte[] encrypt(OperationData data) throws Exception {
	return encrypt(serialize(data));
    }

}
