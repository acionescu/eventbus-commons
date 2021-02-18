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
package net.segoia.util.data.storage;

import java.io.UnsupportedEncodingException;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventsRepository;

public class EventJsonTranslator implements DocumentTranslator<Event>{

    @Override
    public byte[] serialize(Event event) {
	String json = event.toJson();
	try {
	    return json.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException("Serialization failed "+json,e);
	}
    }

    @Override
    public Event deserialize(byte[] bytes) {
	try {
	    String json = new String(bytes, "UTF-8");
	    return EventsRepository.getInstance().fromJson(json);
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException("Deserialization failed "+new String(bytes), e);
	}
    }

}
