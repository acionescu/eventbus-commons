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
package net.segoia.event.eventbus.peers.vo.auth.id;

import net.segoia.event.eventbus.peers.vo.session.KeyDef;

public class SpkiFullIdentityType extends IdentityType {
    public static final String TYPE = "SPKI_FULL";

    private KeyDef keyDef;

    public SpkiFullIdentityType(KeyDef keyDef) {
	this();
	this.keyDef = keyDef;
    }

    public SpkiFullIdentityType() {
	super(TYPE);
    }

    public KeyDef getKeyDef() {
	return keyDef;
    }

    public void setKeyDef(KeyDef keyDef) {
	this.keyDef = keyDef;
    }

}
