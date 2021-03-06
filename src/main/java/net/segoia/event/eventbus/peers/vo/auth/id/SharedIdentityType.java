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

public class SharedIdentityType extends IdentityType {
    public static final String TYPE = "SHARED";

    private KeyDef keyDef;

    public SharedIdentityType() {
	super(TYPE);
    }
    
    

    public SharedIdentityType( KeyDef keyDef) {
	this();
	this.keyDef = keyDef;
    }



    public KeyDef getKeyDef() {
	return keyDef;
    }

    public void setKeyDef(KeyDef keyDef) {
	this.keyDef = keyDef;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((keyDef == null) ? 0 : keyDef.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SharedIdentityType other = (SharedIdentityType) obj;
	if (keyDef == null) {
	    if (other.keyDef != null)
		return false;
	} else if (!keyDef.equals(other.keyDef))
	    return false;
	return true;
    }

}
