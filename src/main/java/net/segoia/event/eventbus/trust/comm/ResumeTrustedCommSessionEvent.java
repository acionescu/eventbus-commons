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
package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:RESUME_SESSION")
public class ResumeTrustedCommSessionEvent extends CustomEvent<ResumeTrustedCommSessionData>{
    public static final String ET="TRUST:COMM:RESUME_SESSION";
    
    public ResumeTrustedCommSessionEvent(ResumeTrustedCommSessionData data) {
	super(ET, data);
    }
    
    public ResumeTrustedCommSessionEvent() {
	super(ET);
    }

    @Override
    public ResumeTrustedCommSessionData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(ResumeTrustedCommSessionData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    

}
