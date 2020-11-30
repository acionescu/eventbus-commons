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

import java.util.List;
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.StrictChannelMatchCondition;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.FilteringEventBus;
import net.segoia.event.eventbus.peers.security.CryptoHelper;
import net.segoia.event.eventbus.peers.security.SpkiPrivateIdentityManager;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;
import net.segoia.event.eventbus.peers.vo.auth.id.SpkiNodeIdentity;
import net.segoia.event.eventbus.peers.vo.bind.ConnectToPeerRequest;
import net.segoia.event.eventbus.peers.vo.bind.DisconnectFromPeerRequest;
import net.segoia.event.eventbus.services.EventNodeServicesManager;
import net.segoia.event.eventbus.vo.security.EventNodeSecurityException;
import net.segoia.event.eventbus.vo.security.IdsLinkData;
import net.segoia.event.eventbus.vo.security.NodeIdLinkData;
import net.segoia.event.eventbus.vo.services.EventNodePublicServiceDesc;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

/**
 * A context for the event node agents that are only aware of the local context
 * 
 * @author adi
 *
 */
public class LocalAgentEventNodeContext {
    private String id;
    private EventNodeContext nodeContext;
    public static final String LOCAL = "LOCAL";

    public LocalAgentEventNodeContext(EventNodeContext nodeContext) {
	super();
	this.nodeContext = nodeContext;
	this.id = nodeContext.generatePeerId();
    }

    public <E extends Event> void addEventHandler(Class<E> eventClass, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(eventClass, handler);
    }

    public <E extends Event> void addEventHandler(String eventType, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(eventType, handler);
    }

    public <E extends Event> void addEventHandler(CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(handler);
    }

    public <E extends Event> void addEventHandler(Condition cond, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(cond, handler);
    }

    public void postEvent(Event event) {
	event.getHeader().setChannel(LOCAL);
	nodeContext.postEvent(event);
    }
    
    public void postEvent(EventContext ec) {
	ec.getEvent().getHeader().setChannel(LOCAL);
	nodeContext.postEvent(ec);
    }

    public void registerToPeer(ConnectToPeerRequest request) {
	nodeContext.getNode().registerToPeer(request);
    }

    public void disconnectFromPeer(DisconnectFromPeerRequest request) {
	nodeContext.getNode().disconnectFromPeer(request);
    }

    public FilteringEventBus getEventBusForCondition(Condition cond) {
	return nodeContext.getNode().getEventBus(cond, true);
    }

    public FilteringEventBus getLocalBus() {
	StrictChannelMatchCondition localCond = new StrictChannelMatchCondition(LOCAL);
	return getEventBusForCondition(localCond);
    }

    public EventNodeContext getNodeContext() {
	return nodeContext;
    }

    public EventNodeLogger getLogger() {
	return nodeContext.getLogger();
    }

    public String getId() {
	return id;
    }

    public void storeIdsLinkData(IdsLinkData data) {
	nodeContext.getSecurityManager().storeIdsLinkData(data);
    }

    public IdsLinkData getIdsLinkData(String idsLinkKey) {
	return nodeContext.getSecurityManager().getIdsLinkData(idsLinkKey);
    }

    public void removeIdsLinkData(String idsLinkKey) {
	nodeContext.getSecurityManager().removeIdsLinkData(idsLinkKey);
    }

    public Map<String, NodeIdLinkData> getAllLinksForIdKey(String idKey) {
	return nodeContext.getSecurityManager().getAllLinksForIdKey(idKey);
    }

    public void scheduleEvent(final Event event, long delay) {
	event.getHeader().setChannel(LOCAL);
	nodeContext.getNode().scheduleEvent(event, delay);
    }

    public NodeIdentityProfile getNodeIdProfile(String nodeIdKey) {
	return nodeContext.getSecurityManager().getIdentityProfile(nodeIdKey);
    }

    public void logInfo(String message) {
	getLogger().info(message);
    }

    public void logDebug(String message) {
	getLogger().debug(message);
    }

    public void logError(String message, Throwable t) {
	getLogger().error(message, t);
    }

    public void logError(String message) {
	getLogger().error(message);
    }
    
    public boolean isDebugEnabled() {
	return getLogger().isDebugEnabled();
    }
    
    public void debug(String msg) {
	getLogger().debug(id+": "+msg );
    }
    
    public CryptoHelper crypto() {
	return nodeContext.getSecurityManager().getCryptoHelper();
    }
    
    public SpkiNodeIdentity getDefaultNodeIdentity() {
	try {
	    return nodeContext.getSecurityManager().getDefaultIdentityManager(SpkiPrivateIdentityManager.class).getPublicNodeIdentity();
	} catch (EventNodeSecurityException e) {
	    logError("Faield to get default node identity",e);
	    return null;
	}
    }
    
    public List<EventNodePublicServiceDesc> getNodePublicServices(){
	return nodeContext.getServicesManager().getPublicServicesDesc();
    }
    
    public EventNodeServicesManager getServicesManager() {
	return nodeContext.getServicesManager();
    }
    
    public boolean isEventLocal(Event event) {
	return LOCAL.equals(event.getHeader().getChannel());
    }
}
