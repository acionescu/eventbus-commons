package net.segoia.event.eventbus.test;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventContextListener;
import net.segoia.event.eventbus.SharedDataContext;

public class TestEventContext extends EventContext{
    private Map<EventContextListener, EventListenerStats> listenersStats = new HashMap<>();

    public TestEventContext(Event event, EventContextListener lifecycleListener) {
	super(event, lifecycleListener);
	// TODO Auto-generated constructor stub
    }

    public TestEventContext(Event event, SharedDataContext dataContext) {
	super(event, dataContext);
	// TODO Auto-generated constructor stub
    }

    public TestEventContext(Event event) {
	super(event);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void visitListener(EventContextListener listener) {
	super.visitListener(listener);
	getStatsForListener(listener).onEvent(this);
    }
    
    private  EventListenerStats getStatsForListener(EventContextListener l) {
	EventListenerStats s = listenersStats.get(l);
	if(s == null) {
	    s = new EventListenerStats();
	    listenersStats.put(l, s);
	}
	return s;
    }

}
