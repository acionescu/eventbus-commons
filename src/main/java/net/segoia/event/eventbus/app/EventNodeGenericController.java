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
package net.segoia.event.eventbus.app;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.PassthroughCustomEventContextListenerFactory;
import net.segoia.event.eventbus.peers.CustomEventHandler;

public abstract class EventNodeGenericController<C> {
    protected C controllerContext;

    protected FilteringEventProcessor eventsProcessor = new FilteringEventProcessor(
	    new PassthroughCustomEventContextListenerFactory());

    protected FilteringEventProcessor localEventsProcessor = new FilteringEventProcessor(
	    new PassthroughCustomEventContextListenerFactory());

    private Map<String, EventNodeGenericController<?>> nestedControllers = new HashMap<>();

    public EventNodeGenericController(C controllerContext) {
	super();
	init(controllerContext);
    }

    public EventNodeGenericController() {
	super();
    }

    protected abstract void registerEventHandlers();

    public void init(C controllerContext) {
	this.controllerContext = controllerContext;
	registerEventHandlers();
    }
    
    /**
     * Override this to handle termination of this controller
     */
    public void terminate() {
	
    }

    public void processEvent(EventContext ec) {
	eventsProcessor.processEvent(ec);

	for (EventNodeGenericController nac : nestedControllers.values()) {
	    nac.processEvent(ec);
	}
    }

    public void processLocalEvent(EventContext ec) {
	localEventsProcessor.processEvent(ec);

	for (EventNodeGenericController nac : nestedControllers.values()) {
	    nac.processLocalEvent(ec);
	}
    }

    public void addNestedController(String id, EventNodeGenericController<?> controller) {
	nestedControllers.put(id, controller);
    }

    public void removeNestedController(String id) {
	nestedControllers.remove(id);
    }

    protected <E extends Event> void addEventHandler(Class<E> eventClass, CustomEventHandler<E> handler) {
	eventsProcessor.addEventHandler(eventClass, handler);
    }

    protected <E extends Event> void addEventHandler(CustomEventHandler<E> handler) {
	eventsProcessor.addEventHandler(handler);
    }

    protected <E extends Event> void addEventHandler(CustomEventHandler<E> handler, int priority) {
	eventsProcessor.addEventHandler(handler, priority);
    }

    protected void addEventHandler(String eventType, CustomEventHandler<Event> handler) {
	eventsProcessor.addEventHandler(eventType, handler);
    }

    protected <E extends Event> void addEventHandler(Condition cond, CustomEventHandler<E> handler) {
	eventsProcessor.addEventHandler(cond, handler);
    }
    
}
