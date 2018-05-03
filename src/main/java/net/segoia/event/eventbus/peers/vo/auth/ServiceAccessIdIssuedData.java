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
package net.segoia.event.eventbus.peers.vo.auth;

import java.util.List;

import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.vo.services.ServiceContract;

public class ServiceAccessIdIssuedData {
    private NodeIdentity<?> accessIdentity;
    private List<ServiceContract> servicePolicies;

    public ServiceAccessIdIssuedData(NodeIdentity<?> accessIdentity, List<ServiceContract> servicePolicies) {
	super();
	this.accessIdentity = accessIdentity;
	this.servicePolicies = servicePolicies;
    }

    public ServiceAccessIdIssuedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public NodeIdentity<?> getAccessIdentity() {
	return accessIdentity;
    }

    public void setAccessIdentity(NodeIdentity<?> accessIdentity) {
	this.accessIdentity = accessIdentity;
    }

    public List<ServiceContract> getServicePolicies() {
	return servicePolicies;
    }

    public void setServicePolicies(List<ServiceContract> servicePolicies) {
	this.servicePolicies = servicePolicies;
    }

}
