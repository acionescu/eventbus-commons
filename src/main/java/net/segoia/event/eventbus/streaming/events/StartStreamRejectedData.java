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

import net.segoia.event.eventbus.peers.vo.RejectionReason;

public class StartStreamRejectedData {
    private String streamId;
    private RejectionReason reason;

    public StartStreamRejectedData() {
	super();
    }

    public StartStreamRejectedData(String streamId, int code, String message) {
	super();
	this.streamId = streamId;
	reason = new RejectionReason(code, message);
    }

    public StartStreamRejectedData(String streamId, RejectionReason reason) {
	super();
	this.streamId = streamId;
	this.reason = reason;
    }

    public String getStreamId() {
	return streamId;
    }

    public void setStreamId(String streamId) {
	this.streamId = streamId;
    }

    public RejectionReason getReason() {
	return reason;
    }

    public void setReason(RejectionReason reason) {
	this.reason = reason;
    }

}
