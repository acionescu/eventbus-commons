package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class StrictChannelMatchCondition extends Condition {
    private String channel;

    public StrictChannelMatchCondition(String channel) {
	super(channel);
	this.channel = channel;
    }

    @Override
    public boolean test(EventContext input) {
	return channel.equals(input.getEvent().getHeader().getChannel());
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

}
