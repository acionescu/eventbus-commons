package net.segoia.util.data.storage;

public class SequenceKeyGenerator<D> implements DocumentKeyGenerator<D>{
    private long value;
    
    @Override
    public String getKey(D document) {
	return String.valueOf(value++);
    }

    public long getValue() {
        return value;
    }
}
