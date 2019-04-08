package net.segoia.event.eventbus.vo.security;

import java.util.HashMap;
import java.util.Map;

public class IdsLinkData {
    private String idsLinkKey;
    
    /**
     * A map of link data for each peer, indexed by peer id key
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
