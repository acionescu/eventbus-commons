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

public class ErrorData {
    private int code;
    private String message;

    public ErrorData(int code, String message) {
	super();
	this.code = code;
	this.message = message;
    }

    public ErrorData() {
	super();
    }

    public ErrorData(String message) {
	super();
	this.message = message;
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return "ErrorData [code=" + code + ", message=" + message + "]";
    }

}
