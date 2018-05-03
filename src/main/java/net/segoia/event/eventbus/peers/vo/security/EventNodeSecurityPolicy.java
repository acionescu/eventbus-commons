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

import java.util.Map;

/**
 * Defines the generic security policy that governs the functioning of an EventNode
 * 
 * @author adi
 *
 */
public class EventNodeSecurityPolicy {
    private PeerBindSecurityPolicy peerBindPolicy;

    private Map<String, PeerChannelSecurityPolicy> channelsSecurity;

    public PeerBindSecurityPolicy getPeerBindPolicy() {
	return peerBindPolicy;
    }

    public void setPeerBindPolicy(PeerBindSecurityPolicy peerBindPolicy) {
	this.peerBindPolicy = peerBindPolicy;
    }

    public Map<String, PeerChannelSecurityPolicy> getChannelsSecurity() {
	return channelsSecurity;
    }

    public void setChannelsSecurity(Map<String, PeerChannelSecurityPolicy> channelsSecurity) {
	this.channelsSecurity = channelsSecurity;
    }
    
    public PeerChannelSecurityPolicy getChannelPolicy(String channel) {
	return channelsSecurity.get(channel);
    }

}
