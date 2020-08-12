package net.segoia.event.eventbus.test;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.SimpleEventContextListener;

public class EventListenerStats extends SimpleEventContextListener {
    private Map<String, EventStats> eventsStats = new HashMap<>();

    @Override
    public void onEvent(EventContext ec) {
	String et = ec.getEvent().getEt();
	EventStats es = eventsStats.get(et);
	if (es == null) {
	    es = new EventStats(et);
	    eventsStats.put(et, es);
	}
	es.onEvent(ec);

    }

}
