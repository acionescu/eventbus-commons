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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.EBusVM;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.app.EventNodeControllerContext;
import net.segoia.event.eventbus.app.EventNodeGenericController;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;
import net.segoia.event.eventbus.peers.vo.ClosePeerData;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.streaming.events.EndStreamData;
import net.segoia.event.eventbus.streaming.events.EndStreamEvent;
import net.segoia.event.eventbus.streaming.events.PeerStreamData;
import net.segoia.event.eventbus.streaming.events.PeerStreamEndedData;
import net.segoia.event.eventbus.streaming.events.PeerStreamEndedEvent;
import net.segoia.event.eventbus.streaming.events.PeerStreamStartedEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamAcceptedEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamRejectedData;
import net.segoia.event.eventbus.streaming.events.StartStreamRejectedEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamRequest;
import net.segoia.event.eventbus.streaming.events.StartStreamRequestEvent;
import net.segoia.event.eventbus.streaming.events.StreamContext;
import net.segoia.event.eventbus.streaming.events.StreamData;
import net.segoia.event.eventbus.streaming.events.StreamInfo;
import net.segoia.event.eventbus.streaming.events.StreamPacketData;
import net.segoia.event.eventbus.streaming.events.StreamPacketEvent;
import net.segoia.eventbus.util.data.ListMap;

/**
 * Handles different types of streams
 * 
 * @author adi
 *
 */
public class StreamsManager extends EventNodeGenericController<EventNodeControllerContext> {
    private StreamsManagerConfig config = new StreamsManagerConfig();
    private Map<String, StreamController> streamControllers = new HashMap<>();
    private ListMap<String, String> streamsByPeers = new ListMap<>();
    private ListMap<String, String> streamsByRemotePeers = new ListMap<>();
    public static final String TYPE_SEPARATOR = "/";

    public StreamsManager() {
	super();
	// TODO Auto-generated constructor stub
    }

    public StreamsManager(EventNodeControllerContext controllerContext) {
	super(controllerContext);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void registerEventHandlers() {
	controllerContext.logger().info("Registering streams manager handlers");
	addEventHandler(StartStreamRequestEvent.class, (c) -> {
	    handleStartStreamRequest(c);
	});

	addEventHandler(EndStreamEvent.class, (c) -> {
	    handleEndStream(c);
	});

	addEventHandler(StreamPacketEvent.class, (c) -> {
	    handleStreamPacket(c);
	});

	/* listen on peer left events */
	controllerContext.getGlobalContext().addEventHandler(PeerLeftEvent.class, (c) -> {
	    handlePeerLeft(c);
	});
    }

    private void handleRemotePeerStreamStarted(CustomEventContext<PeerStreamStartedEvent> c) {

    }

    private void handleRemotePeerStreamEnded(CustomEventContext<PeerStreamEndedEvent> c) {

    }

    private void handlePeerLeft(CustomEventContext<PeerLeftEvent> c) {
	PeerLeftEvent event = c.getEvent();
	PeerInfo peerInfo = event.getData();
	String peerId = peerInfo.getPeerId();

	/* remove active streams from this peer */
	List<String> streamsList = streamsByPeers.remove(peerId);

	if (streamsList == null) {
	    /* nothing to do here */
	    return;
	}

	/* remove all the streams for this peer */
	for (String streamSessionId : streamsList) {
	    removeStream(streamSessionId, StreamConstants.PEER_LEFT, c);
	}

    }

    private void handleStartStreamRequest(CustomEventContext<StartStreamRequestEvent> c) {
	StartStreamRequestEvent event = c.getEvent();
	StartStreamRequest data = event.getData();
	EventNodeLogger logger = controllerContext.logger();

//	if(logger.isDebugEnabled()) {
	logger.debug("Processing start stream event ");
//	}

	if (data == null) {
	    return;
	}
	StreamInfo streamInfo = data.getStreamInfo();
	if (streamInfo == null) {
	    return;
	}
	String streamType = streamInfo.getStreamType();

	if (streamType == null) {
	    return;
	}

	String peerId = event.from();
	if (!isTypeAllowed(streamType)) {
	    /* we don't support this type */
	    StartStreamRejectedData rejectionData = new StartStreamRejectedData(streamInfo.getStreamId(),
		    StreamConstants.TYPE_NOT_SUPPORTED);
	    controllerContext.sendToPeer(new StartStreamRejectedEvent(rejectionData), peerId);

	    if (logger.isDebugEnabled()) {
		logger.debug("Discarding start strem event. Stream type not supported " + streamType);
	    }

	    return;
	}

	String streamSessionId = genereateNewSessionId();

	StreamController streamController = null;

	try {
	    /* check if a custom stream controller is provided */
	    CustomStreamControllerContext customControllerContext = c.getLocalData(CustomStreamControllerContext.class);
	    if (customControllerContext != null) {
		streamController = customControllerContext.getStreamController();
		if (streamController != null) {
		    StreamContext streamContext = new StreamContext(streamSessionId, streamInfo, peerId);

		    /* initialize the stream controller with the context */
		    streamController.init(
			    new StreamControllerContext(this, controllerContext.getGlobalContext(), streamContext));

		    /* add the stream controller */
		    addStreamController(streamSessionId, streamController, peerId);
		}
	    } else {
		streamController = addStreamController(streamSessionId, streamInfo, peerId);
	    }
	} catch (Exception e) {
	    /* expect the controller to handle messaging with the peer and return */
	    return;
	}

	/* send stream accepted event */
	StreamData streamData = new StreamData(streamSessionId, streamInfo);
	StartStreamAcceptedEvent streamAcceptedEvent = new StartStreamAcceptedEvent(streamData);

	PeerStreamData peerStreamData = new PeerStreamData(peerId, streamData);

	PeerStreamStartedEvent peerStreamStartedEvent = new PeerStreamStartedEvent(peerStreamData);

	event.setAsCauseFor(peerStreamStartedEvent);

	/* post a peer stream started event locally */
	controllerContext.postEvent(peerStreamStartedEvent);
	/* send to the peer */
	controllerContext.sendToPeer(streamAcceptedEvent, peerId);

	/* delegate event to the stream controller */
	streamController.processEvent(c);
    }

    private void handleStreamPacket(CustomEventContext<StreamPacketEvent> c) {
	StreamPacketEvent event = c.getEvent();
	StreamPacketData data = event.getData();

	if (data == null) {
	    return;
	}
	String streamSessionId = data.getStreamSessionId();

	if (streamSessionId == null) {
	    return;
	}

	StreamController streamController = streamControllers.get(streamSessionId);
	if (streamController != null) {
	    StreamContext streamContext = streamController.getStreamContext();
	    /* check the message received is from the peer that initiated this stream */
	    if (!streamContext.getSourcePeerId().equals(event.from())) {
		// TODO: might consider to raise an alert
		return;
	    }
	    /* delegate to the controller */
	    streamController.processEvent(c);
	}
    }

    private void handleEndStream(CustomEventContext<EndStreamEvent> c) {
	EndStreamEvent event = c.getEvent();
	EndStreamData data = event.getData();

	if (data == null) {
	    return;
	}
	String streamSessionId = data.getStreamSessionId();

	if (streamSessionId == null) {
	    return;
	}
	
	if(!isStreamSessionValid(streamSessionId)) {
	    /* this is not for us */
	    return;
	}
	
	/* remove this session for the current peer */
	String peerId = event.from();
	boolean removed = streamsByPeers.remove(peerId, streamSessionId);

	if (!removed) {

	    controllerContext.getGlobalContext()
		    .logError("Got invalid end stream event from " + peerId + " for session " + streamSessionId);
	    return;
	}

	removeStream(streamSessionId, data.getReason(), c);

    }

    public void removeStream(String streamSessionId, ClosePeerData closeReason, EventContext causeEventContext) {
	Event causeEvent = causeEventContext.getEvent();
	StreamController sc = streamControllers.remove(streamSessionId);

	if (sc == null) {
	    return;
	}

	/* delegate to the stream controller as well */
	sc.processEvent(causeEventContext);

	StreamContext streamContext = sc.getStreamContext();

	PeerStreamData peerStreamData = new PeerStreamData(streamContext.getSourcePeerId(),
		new StreamData(streamSessionId, streamContext.getStreamInfo()));

	PeerStreamEndedEvent peerStreamEndedEvent = new PeerStreamEndedEvent(
		new PeerStreamEndedData(peerStreamData, closeReason));

	causeEvent.setAsCauseFor(peerStreamEndedEvent);

	/* post a peer stream ended event */
	controllerContext.postEvent(peerStreamEndedEvent);
    }

    private String genereateNewSessionId() {
	String streamSessionId = null;
	/* make sure we generate a unique session */
	do {
	    streamSessionId = EBusVM.getInstance().getHelper().generateSessionId();
	} while (streamControllers.containsKey(streamSessionId));

	return streamSessionId;
    }

    private StreamController addStreamController(String streamSessionId, StreamInfo streamInfo, String peerId) {
	StreamContext streamContext = new StreamContext(streamSessionId, streamInfo, peerId);

	StreamController streamController = new StreamController(
		new StreamControllerContext(this, controllerContext.getGlobalContext(), streamContext));

	streamControllers.put(streamSessionId, streamController);
	streamsByPeers.add(peerId, streamSessionId);

	return streamController;
    }

    private void addStreamController(String streamSessionId, StreamController streamController, String peerId) {

	streamControllers.put(streamSessionId, streamController);
	streamsByPeers.add(peerId, streamSessionId);
    }

    private void addStreamController(String peerId, StreamData streamData) {
	addStreamController(streamData.getStreamSessionId(), streamData.getStreamInfo(), peerId);
    }

    private boolean isTypeAllowed(String streamType) {
	/* we have to use the helper method to be compatible with CN1 as well */
	String[] types = EBusVM.getInstance().getHelper().splitString(streamType, TYPE_SEPARATOR);

	List<String> allowedMainTypes = config.getAllowedMainTypes();
	/* return true if no allowed types are defined or the type is present in the list */
	return allowedMainTypes == null || allowedMainTypes.contains(types[0]);
    }

    public boolean isStreamSessionValid(String sessionId) {
	return streamControllers.containsKey(sessionId);
    }

    public StreamContext getStreamDataBySession(String streamSessionId) {
	StreamController sc = streamControllers.get(streamSessionId);
	if (sc == null) {
	    return null;
	}
	return sc.getStreamContext();
    }

    public void addRemotePeerStream(String remotePeerId, StreamData streamData) {
	addStreamController(remotePeerId, streamData);
    }

//    public boolean removeRemotePeerStream(String remotePeerId, String streamSessionId) {
//	return streamsByRemotePeers.remove(remotePeerId, streamSessionId);
//    }
//    
//    public boolean isRemotePeerStreamSessionValid(String remotePeerId, String streamSessionId) {
//	return streamsByRemotePeers.containsValueForKey(remotePeerId, streamSessionId);
//    }

    public StreamsManagerConfig getConfig() {
	return config;
    }

    public void setConfig(StreamsManagerConfig config) {
	this.config = config;
    }

}
