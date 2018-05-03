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

public class SpkiNodeIdentityType extends IdentityType {
    public static final String TYPE = "SPKI";

    private String algorithm;

    private int keySize;

    public SpkiNodeIdentityType() {
	super(TYPE);
    }

    public SpkiNodeIdentityType(String algorithm, int keySize) {
	this();
	this.algorithm = algorithm;
	this.keySize = keySize;
    }

    public int getKeySize() {
	return keySize;
    }

    public void setKeySize(int keySize) {
	this.keySize = keySize;
    }

    public String getAlgorithm() {
	return algorithm;
    }

    public void setAlgorithm(String algorithm) {
	this.algorithm = algorithm;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
	result = prime * result + keySize;
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
	SpkiNodeIdentityType other = (SpkiNodeIdentityType) obj;
	if (algorithm == null) {
	    if (other.algorithm != null)
		return false;
	} else if (!algorithm.equals(other.algorithm))
	    return false;
	if (keySize != other.keySize)
	    return false;
	return true;
    }

}
