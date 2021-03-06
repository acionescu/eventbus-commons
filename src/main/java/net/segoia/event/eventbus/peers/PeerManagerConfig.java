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
import net.segoia.event.conditions.ConditionsUtil;
import net.segoia.event.conditions.FalseCondition;
import net.segoia.event.conditions.TrueCondition;
import net.segoia.event.eventbus.peers.events.NewPeerEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.peers.events.bind.ConnectToPeerRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.DisconnectFromPeerRequestEvent;

public class PeerManagerConfig {
    /**
     * If set to true, will allow relays on received events <br/>
     * Defaults to false
     */
    private boolean allowPeerRelays;

    private List<PeerManagerAgent> peerManagerAgents;
    private Condition peerBindAcceptCondition = new TrueCondition();
    private Condition peerAuthCondition;
    private Condition peerEventAcceptCondition = ConditionsUtil.buildRejectedEventsList("core-peer-events-guard",
	    ConnectToPeerRequestEvent.ET, DisconnectFromPeerRequestEvent.ET, NewPeerEvent.ET, PeerAcceptedEvent.ET, PeerLeavingEvent.ET, PeerLeftEvent.ET);
    /**
     * Controls what events can be forwarded to this peer
     */
    private Condition eventsForwardingCondition = new FalseCondition();

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

    public Condition getEventsForwardingCondition() {
	return eventsForwardingCondition;
    }

    public void setEventsForwardingCondition(Condition eventsForwardingCondition) {
	this.eventsForwardingCondition = eventsForwardingCondition;
    }

    public Condition getPeerAuthCondition() {
        return peerAuthCondition;
    }

    public void setPeerAuthCondition(Condition peerAuthCondition) {
        this.peerAuthCondition = peerAuthCondition;
    }

}
