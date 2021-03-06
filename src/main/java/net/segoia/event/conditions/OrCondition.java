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
package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class OrCondition extends AggregatedCondition {

    public OrCondition(String id, Condition[] subconditions) {
        super(id, subconditions);
    }
    

    public OrCondition(Condition... subconditions) {
        super(buildId(subconditions), subconditions);
    }
    
    public static OrCondition build(String id, Condition... subconditions) {
	return new OrCondition(id, subconditions);
    }

    private static String buildId(Condition... subconditions) {
        StringBuffer out = new StringBuffer();
        out.append(subconditions[0].getId());
//	Arrays.stream(subconditions).skip(1).map(c -> "|" + c.getId()).forEach(out::append);
        for (int i = 1; i < subconditions.length; i++) {
            out.append("|").append(subconditions[i].getId());
        }
        return out.toString();
    }

    @Override
    public boolean test(EventContext input) {
        if (subconditions == null || subconditions.length == 0) {
            return false;
        }
        for (Condition c : subconditions) {
            if (c.test(input)) {
                return true;
            }
        }
        return false;
    }

}
