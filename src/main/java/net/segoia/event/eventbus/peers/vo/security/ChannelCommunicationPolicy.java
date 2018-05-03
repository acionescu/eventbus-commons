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

import java.util.List;

import net.segoia.event.eventbus.peers.vo.comm.NodeCommunicationStrategy;

public class ChannelCommunicationPolicy {
    /**
     * Supported tx strategies in the preferred order
     */
    private List<NodeCommunicationStrategy> supportedTxStrategies;

    /**
     * Supported rx strategies in preferred order ( in tx format )
     */
    private List<NodeCommunicationStrategy> supportedRxStrategies;
    
    /**
     * The policy for session management for this channel
     */
    private ChannelSessionPolicy sessionPolicy;

    public List<NodeCommunicationStrategy> getSupportedTxStrategies() {
	return supportedTxStrategies;
    }

    public void setSupportedTxStrategies(List<NodeCommunicationStrategy> supportedTxStrategies) {
	this.supportedTxStrategies = supportedTxStrategies;
    }

    public List<NodeCommunicationStrategy> getSupportedRxStrategies() {
	return supportedRxStrategies;
    }

    public void setSupportedRxStrategies(List<NodeCommunicationStrategy> supportedRxStrategies) {
	this.supportedRxStrategies = supportedRxStrategies;
    }

    public ChannelSessionPolicy getSessionPolicy() {
        return sessionPolicy;
    }

    public void setSessionPolicy(ChannelSessionPolicy sessionPolicy) {
        this.sessionPolicy = sessionPolicy;
    }

}
