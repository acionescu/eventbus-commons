/**
 * event-bus - An event bus framework for event driven programming
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
package net.segoia.event.eventbus.peers.security.rules;

import java.util.List;

import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;

public abstract class PeerEventRulesList {
    private List<PeerEventRule> rules;
    private String type;

    public PeerEventRulesList(String type) {
	super();
	this.type = type;
    }

    public PeerEventRulesList(List<PeerEventRule> rules, String type) {
	super();
	this.rules = rules;
	this.type = type;
    }

    public abstract boolean isSatisfied(PeerStateContext context);

    public boolean matchesAny(PeerStateContext context) {
	for (PeerEventRule r : rules) {
	    if (r.matches(context)) {
		return true;
	    }
	}
	return false;
    }

    public List<PeerEventRule> getRules() {
	return rules;
    }

    public void setRules(List<PeerEventRule> rules) {
	this.rules = rules;
    }

}
