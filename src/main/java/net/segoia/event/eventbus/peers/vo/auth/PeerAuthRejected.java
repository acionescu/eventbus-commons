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
package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.RequestRejectReason;

public class PeerAuthRejected {
    private RequestRejectReason reason;

    public PeerAuthRejected() {
	super();
	// TODO Auto-generated constructor stub
    }

    public PeerAuthRejected(RequestRejectReason reason) {
	super();
	this.reason = reason;
    }

    public RequestRejectReason getReason() {
	return reason;
    }

    public void setReason(RequestRejectReason reason) {
	this.reason = reason;
    }

}
