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
package net.segoia.event.eventbus;

import java.util.HashMap;
import java.util.Map;

public class SharedDataStore {
    private Map<String, SharedDataContext> dataContexts = new HashMap<>();

    public SharedDataContext removeDataContext(String key) {
	return dataContexts.remove(key);
    }

    public SharedDataContext getDataContext(String key, boolean create) {
	SharedDataContext dc = dataContexts.get(key);
	if (dc == null && create) {
	    dc = new SharedDataContext();
	    dataContexts.put(key, dc);
	}
	return dc;
    }

}
