package net.segoia.event.eventbus.peers.security;

import java.util.HashMap;
import java.util.Map;

public class NestedOperationDataSerializer implements OperationDataSerializer {
    private OperationDataSerializer defaultSerializer;
    private Map<Class, OperationDataSerializer> customSerializers;

    public NestedOperationDataSerializer(OperationDataSerializer defaultSerializer) {
	super();
	this.defaultSerializer = defaultSerializer;
    }

    @Override
    public byte[] toByteArray(OperationData opData) {
	if (customSerializers != null) {
	    OperationDataSerializer ds = customSerializers.get(opData.getClass());
	    if (ds != null) {
		return ds.toByteArray(opData);
	    }
	}
	return defaultSerializer.toByteArray(opData);
    }

    public void addSerializer(Class clazz, OperationDataSerializer serializer) {
	if (customSerializers == null) {
	    customSerializers = new HashMap<>();
	}
	customSerializers.put(clazz, serializer);
    }

}
