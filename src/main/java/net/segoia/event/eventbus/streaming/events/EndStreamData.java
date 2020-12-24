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

import net.segoia.event.eventbus.peers.vo.ClosePeerData;

public class EndStreamData {
    private String streamSessionId;
    private byte[] data;
    private ClosePeerData reason;

    public EndStreamData(String streamSessionId, byte[] data, int code, String reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.data = data;

	this.reason = new ClosePeerData(code, reason);
    }

    public EndStreamData(String streamSessionId, int code, String reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.reason = new ClosePeerData(code, reason);
    }

    public EndStreamData(String streamSessionId, ClosePeerData reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.reason = reason;
    }

    public EndStreamData(String streamSessionId, byte[] data, ClosePeerData reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.data = data;
	this.reason = reason;
    }

    public EndStreamData() {
	super();
    }

    public String getStreamSessionId() {
	return streamSessionId;
    }

    public void setStreamSessionId(String streamSessionId) {
	this.streamSessionId = streamSessionId;
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }

    public ClosePeerData getReason() {
	return reason;
    }

    public void setReason(ClosePeerData reason) {
	this.reason = reason;
    }

}
