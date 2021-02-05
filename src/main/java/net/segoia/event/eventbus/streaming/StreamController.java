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

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.app.EventNodeGenericController;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.streaming.events.EndStreamEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamRequestEvent;
import net.segoia.event.eventbus.streaming.events.StreamContext;
import net.segoia.event.eventbus.streaming.events.StreamPacketEvent;

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
	
	addEventHandler(StartStreamRequestEvent.class, (c) -> {
	    handleStartStreamRequest(c);
	});

	addEventHandler(StreamPacketEvent.class, (c) -> {
	    handleStreamPacket(c);
	});

	addEventHandler(EndStreamEvent.class, (c) -> {
	    handleStreamEnd(c);
	});
	
	addEventHandler(PeerLeftEvent.class, (c) -> {
	    terminate();
	});
    }
    
    protected void handleStartStreamRequest(CustomEventContext<StartStreamRequestEvent> c) {
	
    }
    
    protected void handleStreamPacket(CustomEventContext<StreamPacketEvent> c) {
	
    }
    
    protected void handleStreamEnd(CustomEventContext<EndStreamEvent> c) {
	
    }
    
    public StreamContext getStreamContext() {
	return controllerContext.getStreamContext();
    }

}
