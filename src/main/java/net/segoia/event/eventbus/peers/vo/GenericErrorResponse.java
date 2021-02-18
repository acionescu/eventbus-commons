/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
