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

import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;
import net.segoia.event.eventbus.peers.security.rules.PeerEventRulesList;

public class IdentityRole {
    public static final String PEER_AUTH = "PEER_AUTH";
    public static final String SERVICE_ACCESS = "SERVICE_ACCESS";

    private String type;

    private PeerEventRulesList peerEventRules;

    public IdentityRole(String type, PeerEventRulesList peerEventRules) {
	super();
	this.type = type;
	this.peerEventRules = peerEventRules;
    }

    public IdentityRole() {
	super();
	// TODO Auto-generated constructor stub
    }

    public boolean isEventAccepted(PeerStateContext context) {
	if (peerEventRules == null) {
	    return true;
	}
	return peerEventRules.isSatisfied(context);
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public PeerEventRulesList getPeerEventRules() {
	return peerEventRules;
    }

    public void setPeerEventRules(PeerEventRulesList peerEventRules) {
	this.peerEventRules = peerEventRules;
    }

}
