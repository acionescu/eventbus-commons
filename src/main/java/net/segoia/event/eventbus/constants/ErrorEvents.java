package net.segoia.event.eventbus.constants;

import net.segoia.event.eventbus.peers.events.GenericErrorResponseEvent;
import net.segoia.event.eventbus.peers.vo.ErrorData;
import net.segoia.event.eventbus.peers.vo.GenericErrorResponse;

public class ErrorEvents {
    public static final ErrorData EVENT_DATA_MISSING = new ErrorData(15, "Event data field is missing.");

    public static final GenericErrorResponseEvent buildEvenDataMissingError(String eventType) {
	return new GenericErrorResponseEvent(new GenericErrorResponse(eventType, EVENT_DATA_MISSING));
    }

}
