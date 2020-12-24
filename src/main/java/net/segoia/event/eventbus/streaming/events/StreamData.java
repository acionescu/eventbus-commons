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

public class StreamData {
    private String streamSessionId;
    private StreamInfo streamInfo;
    /* first chunk of data for this stream */
    private byte[] startData;

    public StreamData(String streamSessionId, StreamInfo streamInfo) {
	super();
	this.streamSessionId = streamSessionId;
	this.streamInfo = streamInfo;
    }

    public StreamData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public String getStreamSessionId() {
	return streamSessionId;
    }

    public void setStreamSessionId(String streamSessionId) {
	this.streamSessionId = streamSessionId;
    }

    public StreamInfo getStreamInfo() {
	return streamInfo;
    }

    public void setStreamInfo(StreamInfo streamInfo) {
	this.streamInfo = streamInfo;
    }

    public byte[] getStartData() {
        return startData;
    }

    public void setStartData(byte[] startData) {
        this.startData = startData;
    }

}
