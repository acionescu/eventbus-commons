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
package net.segoia.event.eventbus.peers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventNodePeersRegistry {
    /**
     * keep the relays associated with peers
     */
    private Map<String, PeerManager> directPeers = new HashMap<>();

    private Map<String, RemotePeerManager> remotePeers = new HashMap<>();
    
    private Map<String, RemotePeerManager> remotePeersByFullPath=new HashMap<>();

    /**
     * Nodes with which we are still negotiating peering
     */
    private Map<String, PeerManager> pendingPeers = new HashMap<>();

    /**
     * The ids of direct peers registered as agents
     */
    private Set<String> agents = new HashSet<>();
    
    
    public boolean setPendingPeerAsDirectPeer(String peerId) {
	PeerManager pm = removePeer(pendingPeers, peerId);
	if(pm == null) {
	    return false;
	}
	setPeerManager(directPeers, pm);
	return true;
    }
    

    public PeerManager getPendingPeerManager(String peerId) {
	return getPeerManger(pendingPeers, peerId);
    }

    public RemotePeerManager getRemotePeerManager(String peerId) {
	return remotePeers.get(peerId);
    }
    
    public RemotePeerManager getRemotePeerManagerByPath(String path) {
	return remotePeersByFullPath.get(path);
    }

    public PeerManager getDirectPeerManager(String peerId) {
	return getPeerManger(directPeers, peerId);
    }

    public PeerManager getPeerManger(Map<String, ? extends PeerManager> targetMap, String peerId) {
	return targetMap.get(peerId);

    }

    public void addAgent(String peerId) {
	agents.add(peerId);
    }

    public void setPendingPeerManager(PeerManager peerManager) {
	setPeerManager(pendingPeers, peerManager);
    }
    
    public void setRemotePeerManager(RemotePeerManager remotePeerManager) {
	setPeerManager(remotePeers, remotePeerManager);
	remotePeersByFullPath.put(remotePeerManager.getFullPath(), remotePeerManager);
    }

    private <P extends PeerManager> void setPeerManager(Map<String, P> targetMap, P peerManager) {
	targetMap.put(peerManager.getPeerId(), peerManager);
    }

    public PeerManager removeRemotePeer(String peerId) {
	 RemotePeerManager peerManager = remotePeers.remove(peerId);
	 if(peerManager != null) {
	     /* remove by full path as well */
	     remotePeersByFullPath.remove(peerManager.getFullPath());
	 }
	 return peerManager;
    }

    public PeerManager removeDirectPeer(String peerId) {
	return removePeer(directPeers, peerId);
    }
    
    public PeerManager removePendingPeer(String peerId) {
	return removePeer(pendingPeers, peerId);
    }

    private PeerManager removePeer(Map<String, ? extends PeerManager> targetMap, String peerId) {
	return targetMap.remove(peerId);
    }

    public Map<String, PeerManager> getDirectPeers() {
	return directPeers;
    }

    public void setDirectPeers(Map<String, PeerManager> directPeers) {
	this.directPeers = directPeers;
    }

    public Map<String, RemotePeerManager> getRemotePeers() {
	return remotePeers;
    }

    public void setRemotePeers(Map<String, RemotePeerManager> remotePeers) {
	this.remotePeers = remotePeers;
    }

    public Set<String> getAgents() {
	return agents;
    }

    public void setAgents(Set<String> agents) {
	this.agents = agents;
    }

}
