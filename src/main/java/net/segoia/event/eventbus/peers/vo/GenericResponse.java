package net.segoia.event.eventbus.peers.vo;

public class GenericResponse {
    private String eventType;
    private GenericResponseData responseData;

    public GenericResponse() {
	super();
    }

    public GenericResponse(String eventType, GenericResponseData responseData) {
	super();
	this.eventType = eventType;
	this.responseData = responseData;
    }

    public String getEventType() {
	return eventType;
    }

    public void setEventType(String eventType) {
	this.eventType = eventType;
    }

    public GenericResponseData getResponseData() {
	return responseData;
    }

    public void setResponseData(GenericResponseData responseData) {
	this.responseData = responseData;
    }

}
