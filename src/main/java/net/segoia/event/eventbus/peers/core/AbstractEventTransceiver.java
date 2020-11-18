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

import net.segoia.event.eventbus.peers.events.ClosePeerEvent;
import net.segoia.event.eventbus.peers.vo.PeerData;
import net.segoia.event.eventbus.peers.vo.PeerErrorData;
import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;

public abstract class AbstractEventTransceiver implements EventTransceiver {

    private PeerDataListener remoteDataListener;

    protected abstract void init();

    public PeerDataListener getRemoteDataListener() {
	return remoteDataListener;
    }

    public void setRemoteDataListener(PeerDataListener remoteDataListener) {
	this.remoteDataListener = remoteDataListener;
    }

    @Override
    public void receiveData(byte[] data) {
	receiveData(new PeerDataEvent(new PeerData(data)));
    }

    @Override
    public void receiveData(PeerDataEvent dataEvent) {
	remoteDataListener.onPeerData(dataEvent);
    }

    @Override
    public void onPeerLeaving(PeerLeavingReason reason) {
	if (remoteDataListener != null) {
	    remoteDataListener.onPeerLeaving(reason);
	}
    }

    @Override
    public void onPeerError(PeerErrorData errorData) {
	if (remoteDataListener != null) {
	    remoteDataListener.onPeerError(errorData);
	}
    }

    @Override
    public void terminate(ClosePeerEvent closeEvent) {
	/* fallback to default terminate */
	terminate();
	
    }
    
    

    // @Override
    // public void receiveEvent(Event event) {
    // remoteEventListener.onPeerEvent(event);
    // }
    //
    // @Override
    // public void onPeerLeaving() {
    // remoteEventListener.onPeerLeaving();
    // }

}
