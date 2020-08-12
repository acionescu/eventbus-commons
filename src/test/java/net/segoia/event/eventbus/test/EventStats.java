package net.segoia.event.eventbus.test;

import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.SimpleEventContextListener;

public class EventStats extends SimpleEventContextListener{
    private String et;
    private long processedCount;

    public EventStats() {
	super();
	// TODO Auto-generated constructor stub
    }

    public EventStats(String et) {
	super();
	this.et = et;
    }

    public void onEvent(EventContext ec) {
	processedCount++;
    }
    
    public long getProcessedCount() {
	return processedCount;
    }

    public void setProcessedCount(long processedCount) {
	this.processedCount = processedCount;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

}
