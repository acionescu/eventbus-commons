package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class StrictSourceAliasMatchCondition extends Condition{
    private String expected;
    
    
    
    public StrictSourceAliasMatchCondition(String expected) {
	super("source_alias_"+expected);
	this.expected = expected;
    }



    @Override
    public boolean test(EventContext input) {
	return expected.equals(input.getEvent().getHeader().getSourceAlias());
    }

}
