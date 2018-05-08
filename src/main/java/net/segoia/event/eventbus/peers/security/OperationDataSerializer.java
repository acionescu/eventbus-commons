package net.segoia.event.eventbus.peers.security;

public interface OperationDataSerializer {
    byte[] toByteArray(OperationData opData);
}
