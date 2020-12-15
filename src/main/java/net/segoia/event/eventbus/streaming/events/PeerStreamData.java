package net.segoia.event.eventbus.streaming.events;

public class PeerStreamData {
    private String sourcePeerId;
    private StreamData streamData;

    public PeerStreamData(String sourcePeerId, StreamData streamData) {
	super();
	this.sourcePeerId = sourcePeerId;
	this.streamData = streamData;
    }

    public PeerStreamData() {
	super();
    }

    public String getSourcePeerId() {
	return sourcePeerId;
    }

    public void setSourcePeerId(String sourcePeerId) {
	this.sourcePeerId = sourcePeerId;
    }

    public StreamData getStreamData() {
	return streamData;
    }

    public void setStreamData(StreamData streamData) {
	this.streamData = streamData;
    }

}
