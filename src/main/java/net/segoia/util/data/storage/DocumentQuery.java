package net.segoia.util.data.storage;

import java.util.Comparator;

public class DocumentQuery<D> {
    private DocumentFilter<D> filter;
    /**
     * Used for sorting if present
     */
    private Comparator<D> comparator;

    /**
     * If greater then 0, it will start query with element at the position indicated by the offset, under the current
     * ordering
     */
    private int offset;

    /**
     * If greater then 0 it will limit the result set to that value
     */
    private int limit;
    
    
    public static <T> DocumentQuery<T> create(Class<T> clazz) {
	return new DocumentQuery<T>();
    }
    
    
    public DocumentQuery<D> setFilter(DocumentFilter<D> filter) {
        this.filter = filter;
        return this;
    }

    public DocumentQuery<D> setComparator(Comparator<D> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DocumentQuery<D> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public DocumentQuery<D> setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public DocumentFilter<D> getFilter() {
	return filter;
    }

    public Comparator<D> getComparator() {
	return comparator;
    }

    public int getOffset() {
	return offset;
    }

    public int getLimit() {
	return limit;
    }

}
