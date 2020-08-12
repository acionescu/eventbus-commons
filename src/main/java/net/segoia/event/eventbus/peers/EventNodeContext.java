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

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventNodeDataManager;
import net.segoia.event.eventbus.peers.security.CryptoHelper;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.util.EventNodeHelper;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.vo.security.SignatureInfo;
import net.segoia.event.eventbus.vo.security.SignedCustomEvent;
import net.segoia.event.eventbus.vo.security.SignedEventData;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;
import net.segoia.event.eventbus.vo.services.EventNodeServiceRef;

/**
 * Defines an {@link EventNode}'s runtime context
 * 
 * @author adi
 *
 */
public class EventNodeContext {
    private EventNode node;
    private EventBusNodeConfig config;
    private EventNodeSecurityManager securityManager;
    private EventNodeHelper helper;
    private EventNodeDataManager dataManager;

    public EventNodeContext(EventNode node, EventNodeSecurityManager securityManager) {
	super();
	this.node = node;
	this.config = node.getConfig();
	this.securityManager = securityManager;
	this.helper = config.getHelper();
	this.dataManager = new EventNodeDataManager();
    }

    public String getLocalNodeId() {
	return node.getId();
    }

    public NodeInfo getNodeInfo() {
	return node.getNodeInfo();
    }

    public String generatePeerId() {
	return helper.generatePeerId();
    }

    public String generateSessionId() {
	return helper.generateSessionId();
    }

    public String generateSecurityToken() {
	return helper.generateSecurityToken();
    }

    public EventNode getNode() {
	return node;
    }

    public EventBusNodeConfig getConfig() {
	return config;
    }

    public EventNodeSecurityManager getSecurityManager() {
	return securityManager;
    }

    public void postEvent(Event event) {
	node.postInternally(event);
	node.postToExtraBusses(event);
    }
    
    public void postEvent(EventContext ec) {
	node.postInternally(ec);
	node.postToExtraBusses(ec);
    }

    public EventNodeServiceDefinition getService(EventNodeServiceRef serviceRef) {
	return node.getService(serviceRef);
    }

    public EventNodeLogger getLogger() {
	return config.getLogger();
    }

    public CryptoHelper crypto() {
	return securityManager.getCryptoHelper();
    }

    public SignedCustomEvent signEvent(Event event) throws Exception {
	String eventJson = event.toJson();
	byte[] eventBytes = eventJson.getBytes("UTF-8");

	SignatureInfo signatureInfo = securityManager.sign(eventBytes);

	return new SignedCustomEvent(new SignedEventData(crypto().base64Encode(eventBytes), signatureInfo));
    }

}
