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

public class EventNodeServiceRef {
    private String serviceId;
    private int serviceVersion;

    public EventNodeServiceRef() {
	super();
    }

    public EventNodeServiceRef(String serviceId, int serviceVersion) {
	super();
	this.serviceId = serviceId;
	this.serviceVersion = serviceVersion;
    }

    public String getServiceId() {
	return serviceId;
    }

    public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
    }

    public int getServiceVersion() {
	return serviceVersion;
    }

    public void setServiceVersion(int serviceVersion) {
	this.serviceVersion = serviceVersion;
    }
    
    

    @Override
    public String toString() {
	return serviceId+"_"+serviceVersion;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
	result = prime * result + serviceVersion;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EventNodeServiceRef other = (EventNodeServiceRef) obj;
	if (serviceId == null) {
	    if (other.serviceId != null)
		return false;
	} else if (!serviceId.equals(other.serviceId))
	    return false;
	if (serviceVersion != other.serviceVersion)
	    return false;
	return true;
    }

}
