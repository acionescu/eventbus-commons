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
package net.segoia.event.eventbus.streaming;

import net.segoia.event.eventbus.app.EventNodeGenericController;
import net.segoia.event.eventbus.streaming.events.StreamContext;

public class StreamController extends EventNodeGenericController<StreamControllerContext>{

    public StreamController() {
	super();
	// TODO Auto-generated constructor stub
    }

    public StreamController(StreamControllerContext controllerContext) {
	super(controllerContext);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void registerEventHandlers() {
	// TODO Auto-generated method stub
	
    }
    
    public StreamContext getStreamContext() {
	return controllerContext.getStreamContext();
    }

}
