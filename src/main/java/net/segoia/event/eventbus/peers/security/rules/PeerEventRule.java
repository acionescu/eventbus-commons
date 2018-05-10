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
package net.segoia.event.eventbus.peers.security.rules;

import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;

public class PeerEventRule extends EventRule {
    public static final String separator = "/";

    private RuleMatcher ruleMatcher;

    public PeerEventRule(RuleMatcher ruleMatcher) {
	super();
	this.ruleMatcher = ruleMatcher;
    }

    public boolean matches(PeerStateContext context) {
	String stateId = context.getState().getId();
	String et = context.getEvent().getEt();

	String signature = stateId + separator + et;

	return matches(new RuleMatchContext(signature));
    }

    public RuleMatcher getRuleMatcher() {
	return ruleMatcher;
    }

    public void setRuleMatcher(RuleMatcher ruleMatcher) {
	this.ruleMatcher = ruleMatcher;
    }

    @Override
    public boolean matches(RuleMatchContext context) {
	return ruleMatcher.matches(context);
    }

}
