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
package net.segoia.event.eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class EventsRepository {
    protected Map<String, Class<?>> eventTypes = new HashMap<>();

    protected Map<Class<?>, String> classToEt = new HashMap<>();

    protected boolean isLoaded;

    protected Set<String> loadedForEventType = new HashSet<>();

    public static EventsRepository instance;

    public static EventsRepository getInstance() {
	return instance;
    }

    public synchronized void checkLoaded() {
	if (!isLoaded) {
	    load();
	}
    }

    public synchronized boolean checkLoaded(String eventType) {
	if (!loadedForEventType.contains(eventType)) {
	    load();
	    loadedForEventType.add(eventType);
	    return false;
	}
	return true;
    }

    public abstract void load();

    public abstract void processClass(Class c);

    public Class<Event> getEventClass(String eventType) {
	checkLoaded();
	Class<?> c = eventTypes.get(eventType);
	if (c == null) {
	    if (!checkLoaded(eventType)) {
		c = eventTypes.get(eventType);
		if (c == null) {
		    return Event.class;
		}
	    } else {
		return Event.class;
	    }
	}
	return (Class<Event>) c;
    }

    public String getEventType(Class<?> c) {
	String t = classToEt.get(c);
	if (t == null) {
	    processClass(c);
	    t = classToEt.get(c);
	}
	return t;
    }

    public void mapEvent(String eventType, Class<?> c) {
	eventTypes.put(eventType, c);
	classToEt.put(c, eventType);
    }

    public abstract Event fromJson(String json);

    public abstract String toJson(Event event);

}
