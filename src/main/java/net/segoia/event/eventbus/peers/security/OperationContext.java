/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public Map<Class, OperationDataDeserializer<?>> getInputDeserializers() {
        return inputDeserializers;
    }

    public void setInputDeserializers(Map<Class, OperationDataDeserializer<?>> inputDeserializers) {
        this.inputDeserializers = inputDeserializers;
    }

    public Map<Class, OperationDataSerializer> getOutputSerializers() {
        return outputSerializers;
    }

    public void setOutputSerializers(Map<Class, OperationDataSerializer> outputSerializers) {
        this.outputSerializers = outputSerializers;
    }
    
    
    
}
