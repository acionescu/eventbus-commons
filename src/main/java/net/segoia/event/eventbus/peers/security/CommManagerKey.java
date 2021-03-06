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

public class CommManagerKey {
    private String ourIdentityType;
    private String peerIdentityType;
    private String sharedIdentityType;
    private String commManagerType;
    
    

    public CommManagerKey() {
	super();
	// TODO Auto-generated constructor stub
    }

    public CommManagerKey(String ourIdentityType, String peerIdentityType, String sharedIdentityType,
	    String commManagerType) {
	super();
	this.ourIdentityType = ourIdentityType;
	this.peerIdentityType = peerIdentityType;
	this.sharedIdentityType = sharedIdentityType;
	this.commManagerType = commManagerType;
    }

    public String getOurIdentityType() {
        return ourIdentityType;
    }

    public void setOurIdentityType(String ourIdentityType) {
        this.ourIdentityType = ourIdentityType;
    }

    public String getPeerIdentityType() {
        return peerIdentityType;
    }

    public void setPeerIdentityType(String peerIdentityType) {
        this.peerIdentityType = peerIdentityType;
    }

    public String getSharedIdentityType() {
        return sharedIdentityType;
    }

    public void setSharedIdentityType(String sharedIdentityType) {
        this.sharedIdentityType = sharedIdentityType;
    }

    public String getCommManagerType() {
        return commManagerType;
    }

    public void setCommManagerType(String commManagerType) {
        this.commManagerType = commManagerType;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((commManagerType == null) ? 0 : commManagerType.hashCode());
	result = prime * result + ((ourIdentityType == null) ? 0 : ourIdentityType.hashCode());
	result = prime * result + ((peerIdentityType == null) ? 0 : peerIdentityType.hashCode());
	result = prime * result + ((sharedIdentityType == null) ? 0 : sharedIdentityType.hashCode());
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
	CommManagerKey other = (CommManagerKey) obj;
	if (commManagerType == null) {
	    if (other.commManagerType != null)
		return false;
	} else if (!commManagerType.equals(other.commManagerType))
	    return false;
	if (ourIdentityType == null) {
	    if (other.ourIdentityType != null)
		return false;
	} else if (!ourIdentityType.equals(other.ourIdentityType))
	    return false;
	if (peerIdentityType == null) {
	    if (other.peerIdentityType != null)
		return false;
	} else if (!peerIdentityType.equals(other.peerIdentityType))
	    return false;
	if (sharedIdentityType == null) {
	    if (other.sharedIdentityType != null)
		return false;
	} else if (!sharedIdentityType.equals(other.sharedIdentityType))
	    return false;
	return true;
    }
    
    
}
