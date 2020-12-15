package net.segoia.event.eventbus.streaming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.EBusVM;
import net.segoia.event.eventbus.app.EventNodeControllerContext;
import net.segoia.event.eventbus.app.EventNodeGenericController;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;
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

	});

	/* listen on peer left events */
	controllerContext.getGlobalContext().addEventHandler(PeerLeftEvent.class, (c) -> {

	});
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

	StreamContext streamContext = new StreamContext(streamSessionId, streamInfo, peerId);

	StreamController streamController = new StreamController(
		new StreamControllerContext(controllerContext.getGlobalContext(), streamContext));

	streamControllers.put(streamSessionId, streamController);
	streamsByPeers.add(peerId, streamSessionId);

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

	/* remove this session for the current peer */
	String peerId = event.from();
	boolean removed = streamsByPeers.remove(peerId, streamSessionId);

	if (!removed) {

	    controllerContext.getGlobalContext()
		    .logError("Got invalid end stream event from " + peerId + " for session " + streamSessionId);
	    return;
	}

	StreamController sc = streamControllers.get(streamSessionId);

	if (sc == null) {
	    return;
	}

	StreamContext streamContext = sc.getStreamContext();

	PeerStreamData peerStreamData = new PeerStreamData(streamContext.getSourcePeerId(),
		new StreamData(streamSessionId, streamContext.getStreamInfo()));

	PeerStreamEndedEvent peerStreamEndedEvent = new PeerStreamEndedEvent(
		new PeerStreamEndedData(peerStreamData, data.getReason()));

	event.setAsCauseFor(peerStreamEndedEvent);

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

    private boolean isTypeAllowed(String streamType) {
	/* we have to use the helper method to be compatible with CN1 as well */
	String[] types = EBusVM.getInstance().getHelper().splitString(streamType, TYPE_SEPARATOR);

	List<String> allowedMainTypes = config.getAllowedMainTypes();
	return allowedMainTypes != null && allowedMainTypes.contains(types[0]);
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

    public StreamsManagerConfig getConfig() {
	return config;
    }

    public void setConfig(StreamsManagerConfig config) {
	this.config = config;
    }

}
