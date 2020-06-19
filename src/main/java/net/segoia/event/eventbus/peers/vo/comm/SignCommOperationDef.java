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
package net.segoia.event.eventbus.peers.vo.comm;

public class SignCommOperationDef extends CommOperationDef {

    public static final String TYPE = "S";

    private String hashingAlgorithm;

    public SignCommOperationDef() {
	super(TYPE);
    }

    public SignCommOperationDef(String hashingAlgorithm) {
	super(TYPE);
	this.hashingAlgorithm = hashingAlgorithm;
    }

    public String getHashingAlgorithm() {
	return hashingAlgorithm;
    }

    public void setHashingAlgorithm(String hashingAlgorithm) {
	this.hashingAlgorithm = hashingAlgorithm;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((hashingAlgorithm == null) ? 0 : hashingAlgorithm.hashCode());
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
	SignCommOperationDef other = (SignCommOperationDef) obj;
	if (hashingAlgorithm == null) {
	    if (other.hashingAlgorithm != null)
		return false;
	} else if (!hashingAlgorithm.equals(other.hashingAlgorithm))
	    return false;
	return true;
    }

}
