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
package net.segoia.event.eventbus.services;

import net.segoia.event.eventbus.peers.EventNodeAgent;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;

public class EventNodeServiceContext {
    private EventNodeAgent provider;
    private EventNodeServiceDefinition serviceDef;

    public EventNodeServiceContext(EventNodeAgent provider, EventNodeServiceDefinition serviceDef) {
	super();
	this.provider = provider;
	this.serviceDef = serviceDef;
    }

    public EventNodeServiceContext() {
	super();
	// TODO Auto-generated constructor stub
    }

    public EventNodeAgent getProvider() {
	return provider;
    }

    public void setProvider(EventNodeAgent provider) {
	this.provider = provider;
    }

    public EventNodeServiceDefinition getServiceDef() {
	return serviceDef;
    }

    public void setServiceDef(EventNodeServiceDefinition serviceDef) {
	this.serviceDef = serviceDef;
    }

}
