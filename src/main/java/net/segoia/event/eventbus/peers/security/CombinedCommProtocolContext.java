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

import net.segoia.event.eventbus.peers.vo.comm.CommStrategy;

public class CombinedCommProtocolContext extends SpkiCommProtocolContext {

    private SharedSecretIdentityManager sharedIdentityManager;

    public CombinedCommProtocolContext(CommStrategy txStrategy, CommStrategy rxStrategy,
	    SharedSecretIdentityManager sharedIdentityManager, SpkiPrivateIdentityManager ourIdentityManager,
	    SpkiPublicIdentityManager peerIdentityManager) {
	super(txStrategy, rxStrategy,ourIdentityManager,peerIdentityManager);
	
	this.sharedIdentityManager = sharedIdentityManager;
    }

    public SharedSecretIdentityManager getSharedIdentityManager() {
	return sharedIdentityManager;
    }

   

}
