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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.constants.ErrorEvents;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.vo.services.EventNodePublicServiceDesc;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;

/**
 * A context that is aware of peer events as well as local events
 * 
 * @author adi
 *
 */
public class GlobalAgentEventNodeContext extends LocalAgentEventNodeContext {
    private PeersManager peersManager;
    private List<EventNodeServiceDefinition> providedServices;
    private Map<String, EventNodePublicServiceDesc> publicServices = new HashMap<>();

    /**
     * Remote interop peers
     */
    private Set<String> interopPeersIds = new HashSet<>();

    public GlobalAgentEventNodeContext(EventNodeContext nodeContext, PeersManager peersManager) {
	super(nodeContext);
	this.peersManager = peersManager;
    }

    public List<EventNodeServiceDefinition> getProvidedServices() {
	return providedServices;
    }

    public void setProvidedServices(List<EventNodeServiceDefinition> providedServices) {
	this.providedServices = providedServices;
	publicServices.clear();
	if (providedServices != null) {
	    for (EventNodeServiceDefinition s : providedServices) {
		EventNodePublicServiceDesc sDesc = s.getServiceDesc();
		publicServices.put(sDesc.getServiceId(), sDesc);
	    }
	}
    }

    public Map<String, EventNodePublicServiceDesc> getAgentPublicServices() {
	return publicServices;
    }

    public void forwardTo(Event event, String peerId) {
	peersManager.forwardTo(event, peerId);
    }

    public void forwardTo(Event event, List<String> peerIds) {
	peersManager.forwardTo(event, peerIds);
    }

    public void forwardTo(Event event, Collection<String> peerIds) {
	peersManager.forwardTo(event, peerIds);
    }
    
    public void forwardTo(Event event, Collection<String> peerIds, Collection<String> noForwardList) {
	peersManager.forwardTo(event, peerIds, noForwardList);
    }
    
    public void forwardTo(Event event, Collection<String> peerIds, String... noForwardList) {
	if(noForwardList != null) {
	    peersManager.forwardTo(event, peerIds, Arrays.asList(noForwardList));
	}
	else {
	    peersManager.forwardTo(event, peerIds);
	}
    }

    public void forwardToDirectPeers(Event event) {
	peersManager.forwardToDirectPeers(event);
    }

    public void forwardToAllKnown(Event event) {
	peersManager.forwardToAllKnown(event);
    }

    public Map<String, String> getDirectPeersByRootKey(Collection<String> rootKeys) {
	return peersManager.getDirectPeersByRootKey(rootKeys);
    }

    public void terminatePeer(String peerId) {
	peersManager.terminatePeer(peerId);
    }

    protected PeersManager getPeersManager() {
	return peersManager;
    }

    public String addRemotePeer(String viaPeerId, PeerInfo peerInfo, Event triggerEvent) {
	try {
	    return peersManager.addRemotePeer(viaPeerId, peerInfo, triggerEvent, getId()).getPeerId();
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public String getIdForRemotePeerByPath(String gatewayPeerId, String remotePeerId) {
	return peersManager.getIdForRemotePeerByPath(gatewayPeerId, remotePeerId);
    }

    public boolean unregisterFromPeer(String peerId) {
	return peersManager.unregisterFromPeer(peerId, getId());
    }

    public PeerManager getPeerManager(String peerId) {
	return peersManager.getPeerManagerById(peerId);
    }

    public boolean addInteropPeerId(String peerId) {
	return interopPeersIds.add(peerId);
    }

    public boolean removeInteropPeerId(String peerId) {
	return interopPeersIds.remove(peerId);
    }

    /**
     * Forwards this event to the peers added as interop peers
     * 
     * @param event
     */
    public void forwardToInteropPeers(Event event) {

	String lastRelay = event.getLastRelay();

	for (String peerId : interopPeersIds) {
	    /* check that we didn't get this event via one of the peers */
	    if (peerId.equals(lastRelay)) {
		continue;
	    }

	    /* check that we don't have a noForward for this peer */
	    if (!event.isNoForward(peerId)) {
		if (isDebugEnabled()) {
		    debug("forwarding event to interop peer " + peerId + " -> " + event.toJson());
		}
		/* forward this event to the peer */
		forwardTo(event, peerId);
	    }
	}
    }

    @Override
    public boolean testEventDataPresent(CustomEvent<?> event) {
	
	boolean valid =  super.testEventDataPresent(event);
	
	if(!valid) {
	    String from = event.from();
	    if(from != null) {
		/* send error */
		forwardTo(ErrorEvents.buildEvenDataMissingError(event.getEt()),from);
	    }
	}
	return valid;
    }
    
    
}
