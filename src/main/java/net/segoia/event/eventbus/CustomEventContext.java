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

public class CustomEventContext<E extends Event> extends EventContext {
    /**
     * Keeps a reference to a parent event context
     */
    private EventContext parentContext;

    public CustomEventContext(EventContext ec) {
	super(ec.getEvent());
	this.parentContext = ec;
    }

    public CustomEventContext(Event event) {
	super(event);
    }
    
    public CustomEventContext(Event event, SharedDataContext dataContext) {
	super(event,dataContext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.segoia.event.eventbus.EventContext#event()
     */
    @Override
    public E event() {
	return (E) super.event();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.segoia.event.eventbus.EventContext#getEvent()
     */
    @Override
    public E getEvent() {
	return (E) super.getEvent();
    }

    @Override
    public void addLocalData(Object obj) {
	/* use parent to store data, if available. This allows sharing data across busses */
	if (parentContext != null) {
	    parentContext.addLocalData(obj);
	} else {
	    super.addLocalData(obj);
	}
    }

    @Override
    public <T> T getLocalData(Class<T> clazz) {
	if (parentContext != null) {
	    return parentContext.getLocalData(clazz);
	} else {
	    return super.getLocalData(clazz);
	}
    }

    public SharedDataContext getMainDataContext() {
	if (parentContext != null) {
	    return parentContext.getMainDataContext();
	} else {
	    return getDataContext(true);
	}
    }
    
    

}
