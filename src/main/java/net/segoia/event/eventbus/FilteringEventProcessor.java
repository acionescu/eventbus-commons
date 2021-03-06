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
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.EventClassMatchCondition;
import net.segoia.event.conditions.StrictEventMatchCondition;
import net.segoia.event.eventbus.peers.CustomEventListener;
import net.segoia.event.eventbus.peers.CustomEventHandler;

public class FilteringEventProcessor extends SimpleEventProcessor {
    private Map<Condition, FilteringEventDispatcher> conditionedListeners = new HashMap<>();

    private CustomEventContextListenerFactory eventListenerFactory = new DefaultCustomEventContextListenerFactory();
    private EventHandler eventNotProcessedHandler;

    public FilteringEventProcessor() {
	super();
    }

    public FilteringEventProcessor(CustomEventContextListenerFactory eventListenerFactory) {
	super();
	this.eventListenerFactory = eventListenerFactory;
    }

    public FilteringEventProcessor(EventDispatcher eventDispatcher) {
	super(eventDispatcher);
    }

    @Override
    public boolean processEvent(EventContext ec) {

	boolean p = super.processEvent(ec);
	if (!ec.isProcessed() && eventNotProcessedHandler != null) {
	    eventNotProcessedHandler.handleEvent(ec);
	}
	return p;
    }

    protected FilteringEventDispatcher getListenerForCondition(Condition condition, int priority) {
	FilteringEventDispatcher l = conditionedListeners.get(condition);
	if (l == null) {
	    l = createEventDispatcherForCondition(condition);
	    conditionedListeners.put(condition, l);
	    if (priority >= 0) {
		registerListener(l, priority);
	    } else {
		registerListener(l);
	    }
	}
	return l;
    }

    protected FilteringEventDispatcher createEventDispatcherForCondition(Condition condition) {
	return new FilteringEventDispatcher(condition, new SimpleEventDispatcher());
    }

    /**
     * Registers a listener for a particular condition with no special priority
     * 
     * @param condition
     * @param listener
     */
    public void registerListener(Condition condition, EventContextListener listener) {
	getListenerForCondition(condition, -1).registerListener(listener);
    }

    /**
     * Registers a listener for a particular condition with a given priority
     * 
     * @param condition
     * @param listener
     * @param priority
     *            - this is the priority of this listener among other listeners registered for the same condition
     */
    public void registerListener(Condition condition, EventContextListener listener, int priority) {
	getListenerForCondition(condition, -1).registerListener(listener, priority);
    }

    /**
     * Registers a listener for a particular condition with a given priority for the condition listener and the final
     * listener
     * 
     * @param condition
     * @param cPriority
     *            - the priority of the condition filter among other event bus top level listeners
     * @param listener
     * @param lPriority
     *            - final listener priority, among other listeners registered for this condition
     */
    public void registerListener(Condition condition, int cPriority, EventContextListener listener, int lPriority) {
	getListenerForCondition(condition, cPriority).registerListener(listener, lPriority);
    }

    /**
     * Registers a listener for a particular condition with a given priority for the condition listener
     * 
     * @param condition
     * @param cPriority
     * @param listener
     */
    public void registerListener(Condition condition, int cPriority, EventContextListener listener) {
	getListenerForCondition(condition, cPriority).registerListener(listener);
    }

    public <E extends Event> void addEventHandler(CustomEventHandler<E> handler) {
	registerListener(getCustomEventListener(handler));
    }

    public <E extends Event> void addEventHandler(CustomEventHandler<E> handler, int priority) {
	registerListener(getCustomEventListener(handler), priority);
    }

    public <E extends Event> void addEventHandler(Condition condition, CustomEventHandler<E> handler) {
	registerListener(condition, getCustomEventListener(handler));
    }

    public <E extends Event> void addEventHandler(Class<E> eventClass, CustomEventHandler<E> handler) {
	addEventHandler(new EventClassMatchCondition(eventClass), handler);
    }

    public <E extends Event> void addEventHandler(String eventType, CustomEventHandler<E> handler) {
	addEventHandler(new StrictEventMatchCondition(eventType), handler);
    }

    protected <E extends Event> CustomEventListener<E> getCustomEventListener(CustomEventHandler<E> handler) {
	// return new CustomEventListener<>(handler);
	return eventListenerFactory.build(handler);
    }

    public EventHandler getEventNotProcessedHandler() {
	return eventNotProcessedHandler;
    }

    public void setEventNotProcessedHandler(EventHandler eventNotProcessedHandler) {
	this.eventNotProcessedHandler = eventNotProcessedHandler;
    }
    
    public void removeListener(Condition cond, EventContextListener listener) {
	FilteringEventDispatcher l = conditionedListeners.get(cond);
	if (l != null) {
	    
	    l.removeListener(listener);
	    if(l.listenersCount() == 0) {
		/* remove the dispatcher for this condition altogether if no listeners are registered */
		conditionedListeners.remove(cond);
	    }
	}
    }

    @Override
    public int listenersCount() {
	
	int c = super.listenersCount();
	
	for(FilteringEventDispatcher fed : conditionedListeners.values()) {
	    c += fed.listenersCount();
	}
	
	/* we have to subtract from the total the numer of listeners that handle each condition */
	
	c -= conditionedListeners.size();
	
	return c;
    }
    
    public int listenersForCondCount(Condition cond) {
	FilteringEventDispatcher d = conditionedListeners.get(cond);
	if(d == null) {
	    return 0;
	}
	return d.listenersCount();
    }

}
