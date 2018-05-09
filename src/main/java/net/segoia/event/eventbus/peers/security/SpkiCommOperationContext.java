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

import net.segoia.event.eventbus.peers.core.PublicIdentityManager;
import net.segoia.event.eventbus.peers.vo.comm.CommOperationDef;

public class SpkiCommOperationContext<D extends CommOperationDef, O extends SpkiPrivateIdentityManager, P extends PublicIdentityManager>
	extends OperationContext<D> {
    private O ourIdentity;
    private P peerIdentity;

    public SpkiCommOperationContext(D opDef, O ourIdentity, P peerIdentity) {
	super(opDef);
	this.ourIdentity = ourIdentity;
	this.peerIdentity = peerIdentity;
    }

    public O getOurIdentity() {
	return ourIdentity;
    }

    public P getPeerIdentity() {
	return peerIdentity;
    }
}
