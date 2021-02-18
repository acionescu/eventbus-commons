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
package net.segoia.event.eventbus.constants;

import net.segoia.event.eventbus.peers.events.GenericErrorResponseEvent;
import net.segoia.event.eventbus.peers.vo.ErrorData;
import net.segoia.event.eventbus.peers.vo.GenericErrorResponse;

public class ErrorEvents {
    public static final ErrorData EVENT_DATA_MISSING = new ErrorData(15, "Event data field is missing.");
    
    public static final ErrorData SIMPLE_AUTH_ERROR = new ErrorData(17, "Authorization failed");

    public static final GenericErrorResponseEvent buildEvenDataMissingError(String eventType) {
	return new GenericErrorResponseEvent(new GenericErrorResponse(eventType, EVENT_DATA_MISSING));
    }

}
