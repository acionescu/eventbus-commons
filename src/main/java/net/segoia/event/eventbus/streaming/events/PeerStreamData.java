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
package net.segoia.event.eventbus.streaming.events;

public class PeerStreamData {
    private String sourcePeerId;
    private StreamData streamData;

    public PeerStreamData(String sourcePeerId, StreamData streamData) {
	super();
	this.sourcePeerId = sourcePeerId;
	this.streamData = streamData;
    }

    public PeerStreamData() {
	super();
    }

    public String getSourcePeerId() {
	return sourcePeerId;
    }

    public void setSourcePeerId(String sourcePeerId) {
	this.sourcePeerId = sourcePeerId;
    }

    public StreamData getStreamData() {
	return streamData;
    }

    public void setStreamData(StreamData streamData) {
	this.streamData = streamData;
    }

}
