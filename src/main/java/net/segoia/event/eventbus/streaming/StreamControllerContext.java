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

import net.segoia.event.eventbus.app.GenericEventNodeControllerContext;
import net.segoia.event.eventbus.peers.GlobalAgentEventNodeContext;
import net.segoia.event.eventbus.streaming.events.StreamContext;

public class StreamControllerContext extends GenericEventNodeControllerContext {
    /* the manager of this stream controller */
    private StreamsManager streamManager;
    private StreamContext streamContext;
    private StreamControllerRuntimeData runtimeData= new StreamControllerRuntimeData();

    public StreamControllerContext(StreamsManager streamManager, GlobalAgentEventNodeContext globalContext, StreamContext streamContext) {
	super(globalContext);
	this.streamManager=streamManager;
	this.streamContext=streamContext;
    }

    public StreamContext getStreamContext() {
	return streamContext;
    }

    public void setStreamContext(StreamContext streamContext) {
	this.streamContext = streamContext;
    }

    public StreamControllerRuntimeData getRuntimeData() {
        return runtimeData;
    }

}
