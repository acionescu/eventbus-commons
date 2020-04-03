package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class StrictSourceTypeMatchCondition extends Condition {
    private String expected;

    public StrictSourceTypeMatchCondition(String expected) {
	super("source_type_" + expected);
	this.expected = expected;
    }

    @Override
    public boolean test(EventContext input) {
	return expected.equals(input.getEvent().getHeader().getSourceType());
    }

}
