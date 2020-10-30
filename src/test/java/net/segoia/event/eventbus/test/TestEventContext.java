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
package net.segoia.event.eventbus.test;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventContextListener;
import net.segoia.event.eventbus.SharedDataContext;

public class TestEventContext extends EventContext{
    private Map<EventContextListener, EventListenerStats> listenersStats = new HashMap<>();

    public TestEventContext(Event event, EventContextListener lifecycleListener) {
	super(event, lifecycleListener);
	// TODO Auto-generated constructor stub
    }

    public TestEventContext(Event event, SharedDataContext dataContext) {
	super(event, dataContext);
	// TODO Auto-generated constructor stub
    }

    public TestEventContext(Event event) {
	super(event);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void visitListener(EventContextListener listener) {
	super.visitListener(listener);
	getStatsForListener(listener).onEvent(this);
    }
    
    private  EventListenerStats getStatsForListener(EventContextListener l) {
	EventListenerStats s = listenersStats.get(l);
	if(s == null) {
	    s = new EventListenerStats();
	    listenersStats.put(l, s);
	}
	return s;
    }

}
