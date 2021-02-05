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
