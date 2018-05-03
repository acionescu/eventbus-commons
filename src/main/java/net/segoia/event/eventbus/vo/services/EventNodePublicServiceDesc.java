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
package net.segoia.event.eventbus.vo.services;

public class EventNodePublicServiceDesc {
    private String serviceId;
    private String serviceName;
    private String serviceDesc;
    private int version;

    public EventNodePublicServiceDesc(String serviceId, String serviceName, String serviceDesc, int version) {
	super();
	this.serviceId = serviceId;
	this.serviceName = serviceName;
	this.serviceDesc = serviceDesc;
	this.version = version;
    }

    public EventNodePublicServiceDesc(String serviceId, String serviceName, String serviceDesc) {
	super();
	this.serviceId = serviceId;
	this.serviceName = serviceName;
	this.serviceDesc = serviceDesc;
    }

    public EventNodePublicServiceDesc() {
	super();
    }

    public String getServiceId() {
	return serviceId;
    }

    public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
    }

    public String getServiceName() {
	return serviceName;
    }

    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    public String getServiceDesc() {
	return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
	this.serviceDesc = serviceDesc;
    }

    public int getVersion() {
	return version;
    }

    public void setVersion(int version) {
	this.version = version;
    }

}
