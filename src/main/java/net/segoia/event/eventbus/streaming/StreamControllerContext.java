package net.segoia.event.eventbus.streaming;

import net.segoia.event.eventbus.app.GenericEventNodeControllerContext;
import net.segoia.event.eventbus.peers.GlobalAgentEventNodeContext;
import net.segoia.event.eventbus.streaming.events.StreamContext;

public class StreamControllerContext extends GenericEventNodeControllerContext {
    private StreamContext streamContext;

    public StreamControllerContext(GlobalAgentEventNodeContext globalContext, StreamContext streamContext) {
	super(globalContext);
	this.streamContext=streamContext;
    }

    public StreamContext getStreamContext() {
	return streamContext;
    }

    public void setStreamContext(StreamContext streamContext) {
	this.streamContext = streamContext;
    }

}
