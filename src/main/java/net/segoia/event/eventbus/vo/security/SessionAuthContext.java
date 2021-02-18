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
package net.segoia.event.eventbus.vo.security;

import java.util.HashSet;
import java.util.Set;

import net.segoia.event.conditions.Condition;

public class SessionAuthContext {
    private long createdTs;
    /**
     * Auth conditions verified in this context
     */
    private Set<Condition> authConditions=new HashSet<>();

    public SessionAuthContext() {
	super();
	createdTs=System.currentTimeMillis();
    }

    public long getCreatedTs() {
        return createdTs;
    }
    
    public void addAuthCondition(Condition c) {
	authConditions.add(c);
    }
    
    /**
     * Returns true if the specified condition was previously added
     * @param c
     * @return
     */
    public boolean isVerified(Condition c) {
	return authConditions.contains(c);
    }
}
