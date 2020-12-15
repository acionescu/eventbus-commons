package net.segoia.event.eventbus.app;

import net.segoia.event.eventbus.EBusVM;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.GlobalAgentEventNodeContext;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;

public class GenericEventNodeControllerContext {
    private GlobalAgentEventNodeContext globalContext;

    public GenericEventNodeControllerContext(GlobalAgentEventNodeContext globalContext) {
	super();
	this.globalContext = globalContext;
    }

    public void sendToPeer(Event event, String peerId) {
	globalContext.forwardTo(event, peerId);
    }

    /**
     * Post an event on the local node
     * 
     * @param event
     */
    public void postEvent(Event event) {

	globalContext.postEvent(event);

//   	EBus.postSystemEvent(event);
	EBusVM.getInstance().postSystemEvent(event);
    }

    public EventNodeLogger logger() {
	return globalContext.getLogger();
    }
}
