package net.segoia.util.data.storage;

public interface DocumentSerializer<D> {

    byte[] serialize(D document);
}
