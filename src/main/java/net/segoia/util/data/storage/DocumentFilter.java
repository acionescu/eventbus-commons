package net.segoia.util.data.storage;

public interface DocumentFilter<D> {

    boolean accept(DocumentContext<D> context);
    
}
