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

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This carries an {@link Event} to all the interested {@link EventContextListener} objects
 * 
 * @author adi
 *
 */
public class EventContext {
    private Event event;
    private Deque<Throwable> errorStack;
    /**
     * A listener used to track the lifecycle of an event
     */
    private EventContextListener lifecycleListener;

    /**
     * A way to request the handling of this event by another dispatcher
     */
    private EventDispatcher delegateDispatcher;

    private EventHandle eventHandle;

    /**
     * A flag that can be set by listeners to reflect the fact that this event has been properly processed
     */
    private boolean processed;

    /**
     * Provides a way for local agents to share data when processing an event
     */
    private SharedDataContext dataContext;
    
    /**
     * This flag is set to true after all processing ends on this event context, regardless if it succeeds or not
     */
    private boolean finished;

    public EventContext(Event event) {
	super();
	this.event = event;
    }
    
    /**
     * Allow passing a data context with the event
     * @param event
     * @param dataContext
     */
    public EventContext(Event event, SharedDataContext dataContext) {
	super();
	this.event = event;
	this.dataContext = dataContext;
    }



    public EventContext(Event event, EventContextListener lifecycleListener) {
	super();
	this.event = event;
	this.lifecycleListener = lifecycleListener;
    }

    public void sendLifecycleEvent(EventContext ec) {
	if (lifecycleListener != null) {
	    ec.visitListener(lifecycleListener);
	} else {
	    throw new RuntimeException(
		    "Event context for event " + event.getId() + " has no lifecycle listener defined");
	}
    }

    public void visitListener(EventContextListener listener) {
	try {
	    listener.onEvent(this);
	} catch (Throwable e) {
	    pushError(e);
//	    EBusVM.getInstance().getLogger().error(listener.getClass()+" failed processing "+event.getEt()+" with error "+e.getMessage(),e);

	    throw e;
	}
    }

    public boolean hasLifecycleListener() {
	return (lifecycleListener != null);
    }

    private void pushError(Throwable e) {
	if (errorStack == null) {
	    initErrorStack();
	}
	errorStack.push(e);
    }

    private void initErrorStack() {
	errorStack = new ArrayDeque<>();
    }

    public boolean hasErrors() {
	return (errorStack != null);
    }

    public Deque<Throwable> getErrorStack() {
	return errorStack;
    }

    public Event event() {
	return event;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
	return event;
    }

    public EventTypeConfig getConfigForEventType() {
	return eventHandle.getBus().getConfigForEventType(event.getEt());
    }

    public EventTypeConfig getConfigForEventType(boolean useDefaultIfMissing) {
	return eventHandle.getBus().getConfigForEventType(event.getEt(), useDefaultIfMissing);
    }

    public EventTypeConfig getConfigForEventType(EventTypeConfig defaultConfig) {
	return eventHandle.getBus().getConfigForEventType(event.getEt(), defaultConfig);
    }

    /**
     * Convenient method to post an event to the bus
     * 
     * @param event
     * @return
     */
    public InternalEventTracker postEvent(Event event) {
	return eventHandle.getBus().postEvent(event);
    }

    /**
     * @return the delegateDispatcher
     */
    public EventDispatcher getDelegateDispatcher() {
	return delegateDispatcher;
    }

    /**
     * @param delegateDispatcher
     *            the delegateDispatcher to set
     */
    public void setDelegateDispatcher(EventDispatcher delegateDispatcher) {
	this.delegateDispatcher = delegateDispatcher;
    }

    public boolean dispatch() {
	boolean res=false;
	if (delegateDispatcher != null) {
	    res = delegateDispatcher.dispatchEvent(this);
	}
	setFinished();
	return res;
    }

    public EventHandle getEventHandle() {
	return eventHandle;
    }

    public void setEventHandle(EventHandle eventHandle) {
	this.eventHandle = eventHandle;
    }

    public boolean isProcessed() {
	return processed;
    }

    public void setProcessed(boolean processed) {
	this.processed = processed;
    }
    
    

    public boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        this.finished = true;
    }

    public void addLocalData(Object obj) {
	if (obj != null) {
	    if (dataContext == null) {
		dataContext = new SharedDataContext();
	    }
	    dataContext.addDataForType(obj);
	}
    }

    public <T> T getLocalData(Class<T> clazz) {
	if (dataContext != null) {
	    return dataContext.getDataForType(clazz);
	}
	return null;
    }

    public SharedDataContext getDataContext() {
        return dataContext;
    }
    
    public SharedDataContext getDataContext(boolean create) {
	if(dataContext == null && create) {
	    dataContext=new SharedDataContext();
	}
	return dataContext;
    }
    
    public SharedDataContext getMainDataContext() {
	return getDataContext(true);
    }
}
