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

import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.SimpleEventContextListener;

public class EventListenerStats extends SimpleEventContextListener {
    private Map<String, EventStats> eventsStats = new HashMap<>();

    @Override
    public void onEvent(EventContext ec) {
	String et = ec.getEvent().getEt();
	EventStats es = eventsStats.get(et);
	if (es == null) {
	    es = new EventStats(et);
	    eventsStats.put(et, es);
	}
	es.onEvent(ec);

    }

}
