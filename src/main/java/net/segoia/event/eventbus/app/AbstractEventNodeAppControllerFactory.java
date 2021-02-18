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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventNodeAppControllerFactory<I,C extends EventNodeAppController<?>> implements EventNodeAppControllerFactory<I, C>{
    private List<EventNodeAppControllerFactory<I,C>> supportedVersions = new ArrayList<>();;
    
    @Override
    public C createController(I input) {
	EventNodeAppControllerFactory<I, C> factory = getFactoryForInput(input);
	
	if(factory != null) {
	    return factory.createController(input);
	}
	return null;
    }
    
    abstract EventNodeAppControllerFactory<I, C> getFactoryForInput(I input);
    
    public void addFactory(EventNodeAppControllerFactory<I, C> factory) {
	supportedVersions.add(factory);
    }

}
