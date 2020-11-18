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
package net.segoia.event.eventbus.agents;

import java.util.ArrayList;
import java.util.List;

import net.segoia.event.eventbus.peers.EventNodeAgent;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;

public class AgentRegisterRequest<A extends EventNodeAgent> {
    private A agent;

    /**
     * True if this agent provides public services
     */
    private boolean publicServiceProvider;

    private List<EventNodeServiceDefinition> providedServices;

    public AgentRegisterRequest(A agent, List<EventNodeServiceDefinition> providedServices) {
	super();
	this.agent = agent;
	this.providedServices = providedServices;
    }

    public AgentRegisterRequest(A agent) {
	super();
	this.agent = agent;
    }
    
    public AgentRegisterRequest() {
	super();
	// TODO Auto-generated constructor stub
    }

    public A getAgent() {
	return agent;
    }

    public void setAgent(A agent) {
	this.agent = agent;
    }

    public void setProvidedServices(List<EventNodeServiceDefinition> providedServices) {
	this.providedServices = providedServices;
    }

    public void addService(EventNodeServiceDefinition serviceDef) {
	if (providedServices == null) {
	    providedServices = new ArrayList<>();
	}

	providedServices.add(serviceDef);
    }

    public boolean isPublicServiceProvider() {
	return publicServiceProvider;
    }

    public void setPublicServiceProvider(boolean publicServiceProvider) {
	this.publicServiceProvider = publicServiceProvider;
    }

    public List<EventNodeServiceDefinition> getProvidedServices() {
	return providedServices;
    }

}
