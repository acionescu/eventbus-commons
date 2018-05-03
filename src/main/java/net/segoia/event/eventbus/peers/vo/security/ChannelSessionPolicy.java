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
package net.segoia.event.eventbus.peers.vo.security;

import net.segoia.event.eventbus.peers.vo.comm.CommStrategy;
import net.segoia.event.eventbus.peers.vo.session.KeyDef;

public class ChannelSessionPolicy {
    /**
     * The type of key to use as session keys
     */
    private KeyDef sessionKeyDef;

    /**
     * The strategy used to send the session key to the peer
     */
    private CommStrategy sessionCommStrategy;

    public KeyDef getSessionKeyDef() {
	return sessionKeyDef;
    }

    public void setSessionKeyDef(KeyDef sessionKeyDef) {
	this.sessionKeyDef = sessionKeyDef;
    }

    public CommStrategy getSessionCommStrategy() {
	return sessionCommStrategy;
    }

    public void setSessionCommStrategy(CommStrategy sessionCommStrategy) {
	this.sessionCommStrategy = sessionCommStrategy;
    }

}
