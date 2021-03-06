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
package net.segoia.event.eventbus.builders;

import net.segoia.event.eventbus.Event;

public class ScopeRestrictedEventBuilder extends EventBuilder{
    
    public ScopeRestrictedEventBuilder(EventBuilderContext context, String scope) {
	super(context);
	scope(scope);
    }
    
        

    /* (non-Javadoc)
     * @see net.segoia.event.eventbus.builders.EventBuilder#category(java.lang.String)
     */
    @Override
    public CategoryRestrictedEventBuilder category(String category) {
	return new CategoryRestrictedEventBuilder(context, category);
    }

    /* (non-Javadoc)
     * @see net.segoia.event.eventbus.builders.EventBuilder#name(java.lang.String)
     */
    @Override
    public EventBuilder name(String name) {
	// TODO Auto-generated method stub
	return super.name(name);
    }

    /* (non-Javadoc)
     * @see net.segoia.event.eventbus.builders.EventBuilder#build()
     */
    @Override
    public Event build() {
	// TODO Auto-generated method stub
	return super.build();
    }


}
