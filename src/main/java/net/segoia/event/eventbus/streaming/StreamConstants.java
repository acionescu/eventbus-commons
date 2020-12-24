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

import net.segoia.event.eventbus.peers.vo.ClosePeerData;
import net.segoia.event.eventbus.peers.vo.RejectionReason;

public class StreamConstants {

    	public static final RejectionReason TYPE_NOT_SUPPORTED=new RejectionReason(10,"Type not supported");
    	
    	public static final ClosePeerData PEER_LEFT=new ClosePeerData(1001, "Peer left");
}
