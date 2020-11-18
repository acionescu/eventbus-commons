package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class LocalEventCondition extends Condition{

    public LocalEventCondition() {
	super("LOCAL");
    }

    @Override
    public boolean test(EventContext input) {
	return input.getEvent().isLocal();
    }

}
