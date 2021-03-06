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

import net.segoia.event.eventbus.Event;

public abstract class EventNodeAppController<C extends EventNodeControllerContext>
	extends EventNodeGenericController<C> {
    private EventNodeAppControllerConfig config = new EventNodeAppControllerConfig();

    public EventNodeAppController() {
	super();
    }

    public EventNodeAppController(EventNodeAppControllerConfig config) {
	super();
	this.config = config;
    }

    public EventNodeAppController(C controllerContext, EventNodeAppControllerConfig config) {
	super();
	this.controllerContext = controllerContext;
	this.config = config;
    }

    public EventNodeAppController(C controllerContext) {
	super();
	this.controllerContext = controllerContext;

    }

    protected void sendEventToClient(Event event) {
	controllerContext.sendToClient(event);
    }

    protected void postEvent(Event event) {
	controllerContext.postEvent(event);
    }

    protected void sendToClientAndPost(Event event) {
	controllerContext.sendToClient(event);
	controllerContext.postEvent(event);
    }

    public EventNodeAppControllerConfig getConfig() {
	return config;
    }

    public void setConfig(EventNodeAppControllerConfig config) {
	this.config = config;
    }

    

}
