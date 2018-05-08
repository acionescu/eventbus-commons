package net.segoia.event.eventbus.peers.security;

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.peers.vo.OperationDef;

public class OperationContext<D extends OperationDef> {
    private D opDef;
    
    private Map<Class, OperationDataDeserializer<?>> inputDeserializers;
    private OperationDataSerializer defaultSerializer;
    private Map<Class, OperationDataSerializer> outputSerializers;

    public OperationContext(D opDef) {
	super();
	this.opDef = opDef;
    }

    public D getOpDef() {
	return opDef;
    }

    public void setOpDef(D opDef) {
	this.opDef = opDef;
    }
    
    public <T> void addDeserializer(Class<T> clazz, OperationDataDeserializer<T> deserializer) {
	if(inputDeserializers == null) {
	    inputDeserializers = new HashMap<>();
	}
	inputDeserializers.put(clazz, deserializer);
    }
    
    public void addSerializer(Class clazz, OperationDataSerializer serializer) {
	if(outputSerializers == null) {
	    outputSerializers = new HashMap<>();
	}
	outputSerializers.put(clazz, serializer);
    }
    
    public <T> T deserializeTo(Class<T> clazz, OperationData opData){
	if(clazz.isInstance(opData)) {
	    return (T)opData;
	}
	OperationDataDeserializer<?> d = inputDeserializers.get(clazz);
	return (T)d.toObject(serialize(opData));
    }
    
    public byte[] serialize(OperationData opData) {
	if(outputSerializers != null) {
	    OperationDataSerializer ds = outputSerializers.get(opData.getClass());
	    if(ds != null) {
		return ds.toByteArray(opData);
	    }
	}
	return defaultSerializer.toByteArray(opData);
    }

    public OperationDataSerializer getDefaultSerializer() {
        return defaultSerializer;
    }

    public void setDefaultSerializer(OperationDataSerializer defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }
    
}
