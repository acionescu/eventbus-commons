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
package net.segoia.event.eventbus.peers.vo.session;

public class KeyDef {
    private String algorithm;
    private int keySize;
    
    
    public KeyDef(String algorithm, int keySize) {
	super();
	this.algorithm = algorithm;
	this.keySize = keySize;
    }

    public KeyDef() {
	super();
    }

    public String getAlgorithm() {
  	return algorithm;
      }

      public void setAlgorithm(String algorithm) {
  	this.algorithm = algorithm;
      }

      public int getKeySize() {
  	return keySize;
      }

      public void setKeySize(int keySize) {
  	this.keySize = keySize;
      }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
	result = prime * result + keySize;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	KeyDef other = (KeyDef) obj;
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
