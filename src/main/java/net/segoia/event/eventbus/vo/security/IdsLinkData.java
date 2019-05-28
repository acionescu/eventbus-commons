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
package net.segoia.event.eventbus.vo.security;

import java.util.HashMap;
import java.util.Map;

public class IdsLinkData {
    private String idsLinkKey;
    
    /**
     * A map of link data for each node, indexed by node id key
     */
    private Map<String, NodeIdLinkData> nodesData=new HashMap<>();
    
    private long creationTs;
    
    private long lastUpdateTs;
    
    
    public void addNodeIdLinkData(NodeIdLinkData data) {
	if(nodesData.size() >= 2) {
	    throw new RuntimeException("Only two nodes ids are allowed to link");
	}
	
	nodesData.put(data.getOwnerIdKey(), data);
    }
    
    public NodeIdLinkData getNodeIdLinkData(String nodeIdKey) {
	return nodesData.get(nodeIdKey);
    }
    

    public String getIdsLinkKey() {
        return idsLinkKey;
    }

    public void setIdsLinkKey(String idsLinkKey) {
        this.idsLinkKey = idsLinkKey;
    }

    public Map<String, NodeIdLinkData> getPeersData() {
        return nodesData;
    }

    public void setPeersData(Map<String, NodeIdLinkData> peersData) {
        this.nodesData = peersData;
    }

    public long getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(long creationTs) {
        this.creationTs = creationTs;
    }

    public long getLastUpdateTs() {
        return lastUpdateTs;
    }

    public void setLastUpdateTs(long lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }
    
    
    
    
}
