package net.segoia.event.eventbus.streaming;

import net.segoia.event.eventbus.streaming.events.StartStreamRequestEvent;

/**
 * Used to pas a custom stream controller to the StreamsManager as a local variable on the
 * {@link StartStreamRequestEvent}
 * 
 * @author adi
 *
 */
public class CustomStreamControllerContext {
    /**
     * The stream controller to be used to handle the stream
     */
    private StreamController streamController;

    public CustomStreamControllerContext(StreamController streamController) {
	super();
	this.streamController = streamController;
    }

    public StreamController getStreamController() {
	return streamController;
    }

}
