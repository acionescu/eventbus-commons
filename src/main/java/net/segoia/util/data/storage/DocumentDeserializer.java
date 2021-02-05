package net.segoia.util.data.storage;

public interface DocumentDeserializer<D> {

    D deserialize(byte[] bytes);
}
