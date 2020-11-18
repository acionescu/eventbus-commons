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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.vo.services.EventNodePublicServiceDesc;
import net.segoia.event.eventbus.vo.services.EventNodeServiceRef;

public class EventNodeServicesManager {
    private Map<EventNodeServiceRef, EventNodeServiceContext> services = new HashMap<>();
    private List<EventNodePublicServiceDesc> publicServicesDesc=new ArrayList<>();
 
    public void addService(EventNodeServiceContext serviceContext) {
	EventNodePublicServiceDesc serviceDesc = serviceContext.getServiceDef().getServiceDesc();
	services.put(new EventNodeServiceRef(serviceDesc.getServiceId(), serviceDesc.getVersion()), serviceContext);
	publicServicesDesc.add(serviceDesc);
    }
    
    public void removeService(EventNodeServiceRef serviceRef) {
	EventNodeServiceContext s = services.remove(serviceRef);
	if(s != null) {
	    publicServicesDesc.remove(s.getServiceDef().getServiceDesc());
	}
    }
    
    public EventNodeServiceContext getService(EventNodeServiceRef serviceRef) {
	return services.get(serviceRef);
    }
    
    public List<EventNodePublicServiceDesc> getPublicServicesDesc() {
	return new ArrayList<>(publicServicesDesc);
    }
    
    public EventNodePublicServiceDesc getServiceMatch(String serviceId, Collection<EventNodePublicServiceDesc> l1, Collection<EventNodePublicServiceDesc> l2) {
	if(l1 != null && l2 != null) {
	    for(EventNodePublicServiceDesc s1 : l1) {
		if(!serviceId.equals(s1.getServiceId())) {
		    continue;
		}
		for(EventNodePublicServiceDesc s2 : l2) {
		    if(s1.equals(s2)) {
			return s1;
		    }
		}
	    }
	}
	return null;
    }
}
