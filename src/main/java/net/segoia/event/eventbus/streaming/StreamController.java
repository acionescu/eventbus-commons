package net.segoia.event.eventbus.streaming;

import net.segoia.event.eventbus.app.EventNodeGenericController;
import net.segoia.event.eventbus.streaming.events.StreamContext;

public class StreamController extends EventNodeGenericController<StreamControllerContext>{

    public StreamController() {
	super();
	// TODO Auto-generated constructor stub
    }

    public StreamController(StreamControllerContext controllerContext) {
	super(controllerContext);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void registerEventHandlers() {
	// TODO Auto-generated method stub
	
    }
    
    public StreamContext getStreamContext() {
	return controllerContext.getStreamContext();
    }

}
