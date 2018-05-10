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

public class SimpleEventProcessor {
    private EventDispatcher eventDispatcher = new SimpleEventDispatcher();

    public SimpleEventProcessor(EventDispatcher eventDispatcher) {
	super();
	this.eventDispatcher = eventDispatcher;
    }

    public SimpleEventProcessor() {
	super();
    }

    public void start() {
	eventDispatcher.start();
    }

    public void stop() {
	eventDispatcher.stop();
    }

    public boolean processEvent(EventContext ec) {
	return eventDispatcher.dispatchEvent(ec);
    }

    public void registerListener(EventContextListener listener) {
	eventDispatcher.registerListener(listener);
    }

    public void removeListener(EventContextListener listener) {
	eventDispatcher.removeListener(listener);
    }

    public void registerListener(EventContextListener listener, int priority) {
	eventDispatcher.registerListener(listener, priority);

    }
    
    /**
     * @return the eventDispatcher
     */
    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    /**
     * @param eventDispatcher the eventDispatcher to set
     */
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

}
