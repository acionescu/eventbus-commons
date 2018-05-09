/**
 * event-bus - An event bus framework for event driven programming
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
package net.segoia.event.eventbus.peers.comm;

import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.core.ChainedEventTransceiver;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.PeerDataEvent;
import net.segoia.event.eventbus.peers.security.CommDataContext;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.security.CryptoHelper;

public class CommProtocolEventTransceiver extends ChainedEventTransceiver {
    private PeerContext peerContext;

    private PeerCommManager peerCommManager;

    public CommProtocolEventTransceiver(EventTransceiver downstream, PeerContext peerContext) {
	super(downstream);
	this.peerContext = peerContext;
	init();
    }

    @Override
    protected void init() {
	super.init();

	peerCommManager = peerContext.getPeerCommManager();

    }

    @Override
    public void onPeerData(PeerDataEvent dataEvent) {
	CommDataContext processedData;
	byte[] data = dataEvent.getData().getData();
	try {
	    CryptoHelper cryptoHelper = peerContext.getNodeContext().getSecurityManager().getCryptoHelper();
	    byte[] decodedData = cryptoHelper.base64DecodeToBytes(data);
	    processedData = peerCommManager.processIncomingData(new CommDataContext(decodedData));
	    dataEvent.getData().setData(processedData.getData());
	    receiveData(dataEvent);
	} catch (CommOperationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public void sendData(byte[] data) {
	try {
	    CommDataContext processedData = peerCommManager.processsOutgoingData(new CommDataContext(data));
	    byte[] processedDataBytes = processedData.getData();
	    CryptoHelper cryptoHelper = peerContext.getNodeContext().getSecurityManager().getCryptoHelper();
	    byte[] encodedData = cryptoHelper.base64EncodeToBytes(processedDataBytes);
	    super.sendData(encodedData);
	} catch (CommOperationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public void start() {
	// TODO Auto-generated method stub

    }

}
