package net.segoia.event.eventbus.peers.vo;

public class GenericErrorResponse {
    private String eventType;
    private ErrorData errorData;

    public GenericErrorResponse() {
	super();
	// TODO Auto-generated constructor stub
    }

    public GenericErrorResponse(String eventType, ErrorData errorData) {
	super();
	this.eventType = eventType;
	this.errorData = errorData;
    }

    public String getEventType() {
	return eventType;
    }

    public void setEventType(String eventType) {
	this.eventType = eventType;
    }

    public ErrorData getErrorData() {
	return errorData;
    }

    public void setErrorData(ErrorData errorData) {
	this.errorData = errorData;
    }

}
