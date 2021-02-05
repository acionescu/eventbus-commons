package net.segoia.util.data.storage;

/**
 * Generates a key for a document
 * @author adi
 *
 */
public interface DocumentKeyGenerator<D> {

    String getKey(D document);
}
