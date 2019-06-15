package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class CustomPeerIdCondition extends Condition {
    private String expected;

    public CustomPeerIdCondition(String id, String expected) {
	super(id);
	this.expected = expected;
    }

    @Override
    public boolean test(EventContext input) {
	return expected.equals(input.getEvent().getHeader().getCustomPeerId());
    }

}
