package net.segoia.event.eventbus.streaming;

import java.io.IOException;
import java.io.OutputStream;

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.streaming.events.EndStreamData;
import net.segoia.event.eventbus.streaming.events.EndStreamEvent;
import net.segoia.event.eventbus.streaming.events.StartStreamRequest;
import net.segoia.event.eventbus.streaming.events.StartStreamRequestEvent;
import net.segoia.event.eventbus.streaming.events.StreamPacketData;
import net.segoia.event.eventbus.streaming.events.StreamPacketEvent;

/**
 * Writes the received packets to the provided output stream
 * 
 * @author adi
 *
 */
public abstract class WriteStreamController extends StreamController {
   

    public WriteStreamController() {
	super();
    }
    
    

    /**
     * Implement to write the data
     */
    protected abstract void write(byte[] data) throws Exception;


    private void writeData(byte[] data) {
	if (data == null || data.length == 0) {
	    return;
	}

	try {
//	    outputStream.write(data);
	    write(data);
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	    /* close the stream */
	    terminate();
	}
    }

    @Override
    protected void handleStartStreamRequest(CustomEventContext<StartStreamRequestEvent> c) {
	StartStreamRequestEvent event = c.getEvent();
	StartStreamRequest request = event.getData();
	if (request == null) {
	    return;
	}

	byte[] bytes = request.getData();

	/* write the first chunk of data, if present */
	writeData(bytes);
    }

    @Override
    protected void handleStreamPacket(CustomEventContext<StreamPacketEvent> c) {
	StreamPacketEvent event = c.getEvent();
	StreamPacketData packetData = event.getData();
	if (packetData == null) {
	    return;
	}

	byte[] bytes = packetData.getData();

	writeData(bytes);
    }

    @Override
    protected void handleStreamEnd(CustomEventContext<EndStreamEvent> c) {
	EndStreamEvent event = c.getEvent();
	EndStreamData data = event.getData();

	if (data != null) {
	    byte[] bytes = data.getData();
	    writeData(bytes);
	}
	/* terminate the stream */
	terminate();
    }


}
