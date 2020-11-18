package net.segoia.event.conditions;

public class ConditionsUtil {

    public static Condition buildAcceptedEventsList(String id, String... eventTypes) {
	Condition[] conditions = new Condition[eventTypes.length];
	int ci=0;
	for(String et: eventTypes) {
	    conditions[ci]=new StrictEventMatchCondition(et);
	    ci++;
	}
	return OrCondition.build(id, conditions);
    }
    
    public static Condition buildRejectedEventsList(String id, String... eventTypes) {
	return new NotCondition(id, buildAcceptedEventsList(id+"-acc", eventTypes));
    }
}
