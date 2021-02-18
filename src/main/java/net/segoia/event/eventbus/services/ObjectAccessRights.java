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
package net.segoia.event.eventbus.services;

import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.TrueCondition;

/**
 * This defines the operations that can pe executed on a particular object, by a client. <br>
 * Operations are mapped on particular events received from the client <br>
 * Access rights for a particular event are granted by defining a {@link Condition} for each event type that will
 * allow/disallow an action depending on the event and client properties
 * 
 * @author adi
 *
 */
public class ObjectAccessRights {
    /**
     * Default condition. This will be applied if no specific condition is defined for an event type <br>
     * By default allow everything
     */
    private Condition defaultCondition = new TrueCondition();

    /**
     * Conditions by event type. If a condition is defined here for a particular event type, this will be used.
     */
    private Map<String, Condition> conditionsByEvent;
}
