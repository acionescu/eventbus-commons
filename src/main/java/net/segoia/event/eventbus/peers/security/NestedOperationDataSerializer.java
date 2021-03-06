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
