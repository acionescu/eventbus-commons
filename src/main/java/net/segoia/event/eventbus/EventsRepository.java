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

    public abstract void checkLoaded();

    public abstract boolean checkLoaded(String eventType);

    public abstract void load();

    public abstract void processClass(Class c);

    public abstract Class<Event> getEventClass(String eventType);

    public abstract String getEventType(Class<?> c);
    
    public abstract void mapEvent(String eventType, Class<?> c);
    
    public abstract Event fromJson(String json);
    
    public abstract String toJson(Event event);

}
