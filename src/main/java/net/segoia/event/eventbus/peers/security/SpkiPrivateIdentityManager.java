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

import net.segoia.event.eventbus.peers.core.PrivateIdentityManager;
import net.segoia.event.eventbus.peers.vo.comm.EncryptWithPublicCommOperationDef;
import net.segoia.event.eventbus.peers.vo.comm.SignCommOperationDef;

public interface SpkiPrivateIdentityManager extends PrivateIdentityManager{
//    public byte[] sign(SignCommOperationContext context) throws Exception;
//    public byte[] decryptPrivate(DecryptOperationContext context) throws Exception;
    
    public SignOperationWorker buildSignWorker(SignCommOperationDef opDef) throws Exception;
    public DecryptOperationWorker buildPrivateDecryptWorker(EncryptWithPublicCommOperationDef opDef) throws Exception;
    
    
}
