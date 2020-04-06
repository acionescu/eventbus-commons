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

import net.segoia.event.conditions.generic.SimpleParamCondition;
import net.segoia.event.conditions.providers.EventParamProvider;
import net.segoia.event.eventbus.EventContext;

public class EventParamMatchCondition extends Condition {
    private EventParamProvider paramProvider;
    private SimpleParamCondition condition;

    public EventParamMatchCondition() {
	super(EventParamMatchCondition.class.getSimpleName() + "_" + System.currentTimeMillis());
    }

    @Override
    public boolean test(EventContext input) {
	return condition.test(paramProvider.provide(input));
    }

    public EventParamProvider getParamProvider() {
        return paramProvider;
    }

    public void setParamProvider(EventParamProvider paramProvider) {
        this.paramProvider = paramProvider;
    }

    public SimpleParamCondition getCondition() {
        return condition;
    }

    public void setCondition(SimpleParamCondition condition) {
        this.condition = condition;
    }

}
