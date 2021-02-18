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
