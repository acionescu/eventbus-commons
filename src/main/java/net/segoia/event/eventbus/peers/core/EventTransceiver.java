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
package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;

/**
 * Handles the transmission of events over a particular communication channel
 * @author adi
 *
 */
public interface EventTransceiver {
    void start();
    
    void terminate();
    
//    void sendEvent(Event event);
//    void receiveEvent(Event event);
//    void setRemoteEventListener(PeerEventListener eventListener);
    
    void sendData(byte[] data);
    void receiveData(byte[] data);
    void receiveData(PeerDataEvent dataEvent);
    void setRemoteDataListener(PeerDataListener dataListener);
    
    /**
     * Called when the communication with the peer is interrupted without us initiating it
     */
    void onPeerLeaving(PeerLeavingReason reason);
    
    /**
     * 
     * @return - The communication channel type ( e.g. ws, wss, http, https, etc.. )
     */
    String getChannel();
    
}
