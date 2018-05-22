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
package net.segoia.event.eventbus.peers.events.auth;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;
import net.segoia.event.eventbus.peers.vo.auth.PeerAuthRequest;

@EventType("PEER:AUTH:REQUEST")
public class PeerAuthRequestEvent extends CustomEvent<PeerAuthRequest> {
    public static final String ET = "PEER:AUTH:REQUEST";

    public PeerAuthRequestEvent(String et, PeerAuthRequest data) {
	super(et, data);
	// TODO Auto-generated constructor stub
    }

    public PeerAuthRequestEvent(String et) {
	super(et);
	// TODO Auto-generated constructor stub
    }

    public PeerAuthRequestEvent(PeerAuthRequest data) {
	super(ET, data);

    }

    public PeerAuthRequestEvent() {
	super(ET);
	// TODO Auto-generated constructor stub
    }

    @Override
    public PeerAuthRequest getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(PeerAuthRequest data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
