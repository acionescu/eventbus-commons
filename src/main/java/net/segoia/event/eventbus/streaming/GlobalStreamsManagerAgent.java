package net.segoia.event.eventbus.streaming;

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.peers.GlobalEventNodeAgent;
import net.segoia.event.eventbus.peers.vo.RejectionReason;
import net.segoia.event.eventbus.streaming.events.StartStreamRejectedData;
import net.segoia.event.eventbus.streaming.events.StartStreamRejectedEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamRequest;
import net.segoia.event.eventbus.streaming.events.StartStreamRequestEvent;
import net.segoia.event.eventbus.streaming.events.StreamInfo;

/**
 * This manages the lifecycle of streams and communication with the client in case no stream handler does it
 * 
 * @author adi
 *
 */
public class GlobalStreamsManagerAgent extends GlobalEventNodeAgent {
    public static final RejectionReason STREAM_DATA_MISSING = new RejectionReason(5001, "Stream data missing");
    public static final RejectionReason STREAM_INFO_MISSING = new RejectionReason(5002, "Stream info missing");
    public static final RejectionReason STREAM_APP_ID_MISSING = new RejectionReason(5003,
	    "Stream appId missing");
    public static final RejectionReason STREAM_APP_ID_UNKNOWN = new RejectionReason(5004, "Stream appId unknown");

    @Override
    protected void agentInit() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void config() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerHandlers() {
	context.addEventHandler(StartStreamRequestEvent.class, (c) -> {
	    /* if no other handler processed this, then send an error to the user */
	    if (!c.getEvent().isHandled()) {
		handleStartStreamRequest(c);
	    }
	}, 999); // make sure we register this to be the last executed, to give a chance to the application handlers to
		 // process the event

    }

    @Override
    public void terminate() {
	// TODO Auto-generated method stub

    }

    private void handleStartStreamRequest(CustomEventContext<StartStreamRequestEvent> c) {
	StartStreamRequestEvent event = c.getEvent();
	StartStreamRequest data = event.getData();

	String peerId = event.from();

	if (data == null) {
	    context.forwardTo(new StartStreamRejectedEvent(new StartStreamRejectedData(null, STREAM_DATA_MISSING)),
		    peerId);
	    return;
	}

	StreamInfo streamInfo = data.getStreamInfo();
	if (streamInfo == null) {
	    context.forwardTo(new StartStreamRejectedEvent(new StartStreamRejectedData(null, STREAM_INFO_MISSING)),
		    peerId);
	    return;
	}

	String streamId = streamInfo.getStreamId();

	/* verify that this event was sent by a valid chat peer */
	String appId = streamInfo.getAppId();

	if (appId == null) {
	    /* disregard this if no app id is specified */
	    context.forwardTo(
		    new StartStreamRejectedEvent(new StartStreamRejectedData(streamId, STREAM_APP_ID_MISSING)), peerId);
	    return;
	} else {
	    /* app id not known */
	    context.forwardTo(
		    new StartStreamRejectedEvent(new StartStreamRejectedData(streamId, STREAM_APP_ID_UNKNOWN)), peerId);
	    return;
	}
    }

}
