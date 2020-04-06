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
package net.segoia.event.eventbus.peers;

import java.util.List;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.TrueCondition;

public class PeerManagerConfig {
    /**
     * If set to true, will allow relays on received events
     * <br/>
     * Defaults to false
     */
    private boolean allowPeerRelays;
    
    private List<PeerManagerAgent> peerManagerAgents;
    private Condition peerBindAcceptCondition=new TrueCondition();
    private Condition peerEventAcceptCondition=new TrueCondition();

    public List<PeerManagerAgent> getPeerManagerAgents() {
	return peerManagerAgents;
    }

    public void setPeerManagerAgents(List<PeerManagerAgent> peerManagerAgents) {
	this.peerManagerAgents = peerManagerAgents;
    }

    public boolean isAllowPeerRelays() {
        return allowPeerRelays;
    }

    public void setAllowPeerRelays(boolean allowPeerRelays) {
        this.allowPeerRelays = allowPeerRelays;
    }

    public Condition getPeerEventAcceptCondition() {
        return peerEventAcceptCondition;
    }

    public void setPeerEventAcceptCondition(Condition peerEventAcceptCondition) {
        this.peerEventAcceptCondition = peerEventAcceptCondition;
    }

    public Condition getPeerBindAcceptCondition() {
        return peerBindAcceptCondition;
    }

    public void setPeerBindAcceptCondition(Condition peerBindAcceptCondition) {
        this.peerBindAcceptCondition = peerBindAcceptCondition;
    }

}
