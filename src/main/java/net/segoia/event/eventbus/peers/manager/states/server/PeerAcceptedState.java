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
package net.segoia.event.eventbus.peers.manager.states.server;

import java.util.ArrayList;

import net.segoia.event.eventbus.EBusVM;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.events.auth.ServiceAccessIdIssuedEvent;
import net.segoia.event.eventbus.peers.events.auth.ServiceAccessIdRequestEvent;
import net.segoia.event.eventbus.peers.events.auth.ServiceAccessIdRequestRjectedEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerRequestHandlingException;
import net.segoia.event.eventbus.peers.exceptions.PeerRequestRejectedException;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.util.StringHelper;
import net.segoia.event.eventbus.peers.vo.auth.ServiceAccessIdIssuedData;
import net.segoia.event.eventbus.peers.vo.auth.ServiceAccessIdRequest;
import net.segoia.event.eventbus.peers.vo.auth.ServiceAccessIdRequestRejectedReason;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.vo.security.DataAuthLevel;
import net.segoia.event.eventbus.vo.security.SignatureInfo;
import net.segoia.event.eventbus.vo.security.SignedCustomEvent;
import net.segoia.event.eventbus.vo.security.SignedEventData;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class PeerAcceptedState extends PeerManagerState {
    public static final String ID = "PeerAcceptedState";

    @Override
    public void onEnterState(PeerManager peerManager) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onExitState(PeerManager peerManager) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerLocalEventHandlers() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerPeerEventHandlers() {
	registerPeerEventProcessor(ServiceAccessIdRequestEvent.class, (c) -> {
	    try {
		handleServiceAccessIdRequest(c);
	    } 
	    catch(Throwable t) {
		c.getPeerManager().handleError("Failed handling service id request",t);
	    }
	    finally {
		c.getEvent().setHandled();
	    }
	});
	
	EBusVM.getInstance().getLogger().info("registering signed event handler");
	registerPeerEventProcessor(SignedCustomEvent.class, (c)->{
	    c.getNodeContext().getLogger().info("handling signed event.");
	    try {
		handleSignedEvent(c);
	    } 
	    catch(Throwable t) {
		c.getPeerManager().handleError("Failed handling signed event",t);
	    }
	    finally {
		c.getEvent().setHandled();
	    }
	    
	});

	registerPeerEventProcessor((c) -> {
	    Event event = c.getEvent();
	    if (!event.isHandled()) {
		c.getNodeContext().getLogger().info(event.getEt() + " not handled. Delegating further. "+event.getClass());
		c.getPeerManager().postEvent(event);
	    }
	});

    }
    
    protected void handleSignedEvent(PeerEventContext<SignedCustomEvent> c) {
	SignedCustomEvent event = c.getEvent();
	
	SignedEventData signedEventData = event.getData();
	
	String encodedEventData = signedEventData.getEventData();
	SignatureInfo signatureInfo = signedEventData.getSignatureInfo();
	
	PeerManager peerManager = c.getPeerManager();
	PeerContext peerContext = peerManager.getPeerContext();
	
	EventNodeSecurityManager securityManager = peerContext.getNodeContext().getSecurityManager();
	
	byte[] eventData = securityManager.getCryptoHelper().base64Decode(encodedEventData);
	
	DataAuthLevel dataAuthLevel = securityManager.getDataAuthLevel(peerContext, signatureInfo, eventData);
	
	/* get the actual signed event */
	Event nestedEvent = Event.fromJson(StringHelper.stringFromByteArray(eventData,"UTF-8"),event.getCauseEvent());
	
	nestedEvent.getHeader().setAuthLevel(dataAuthLevel);
	/* !! maybe here should be event.getLastRelay(), as we want the peer that sent as this event to appear as from ?*/
	nestedEvent.getHeader().setFrom(event.from());
	
	c.getPeerManager().postEvent(nestedEvent);
    }

    protected void handleServiceAccessIdRequest(PeerEventContext<ServiceAccessIdRequestEvent> c) {
	ServiceAccessIdRequest request = c.getEvent().getData();
	/* check if the current identity allows this */
	PeerManager peerManager = c.getPeerManager();
	PeerContext peerContext = peerManager.getPeerContext();
	NodeIdentityProfile currentPeerIdentityProfile = peerContext.getCurrentPeerIdentityProfile();

	PeerStateContext peerStateContext = new PeerStateContext(this, peerContext, c.getEvent());

	if (!peerContext.isEventAccepted(peerStateContext)) {
	    /* if the event is not accepted send reject event */
	    ServiceAccessIdRequestRjectedEvent rejectedEvent = new ServiceAccessIdRequestRjectedEvent(
		    new ServiceAccessIdRequestRejectedReason("Request forbidden for your type of authentication"));
	    peerManager.forwardToPeer(rejectedEvent);
	    return;
	}

	EventNodeSecurityManager securityManager = peerContext.getNodeContext().getSecurityManager();

	try {
	    NodeIdentityProfile validIdentityForServiceAccess = securityManager.getValidIdentityForServiceAccess(c,
		    true);
	    NodeIdentity<?> issuedIdentity = validIdentityForServiceAccess.getIdentity();

	    ServiceAccessIdIssuedData serviceAccessIdIssuedData = new ServiceAccessIdIssuedData(issuedIdentity,
		    new ArrayList<>(validIdentityForServiceAccess.getServiceContracts().values()));

	    ServiceAccessIdIssuedEvent serviceAccessIdIssuedEvent = new ServiceAccessIdIssuedEvent(
		    serviceAccessIdIssuedData);

	    peerManager.forwardToPeer(serviceAccessIdIssuedEvent);

	} catch (PeerRequestRejectedException e) {
	    peerManager.forwardToPeer(
		    new ServiceAccessIdRequestRjectedEvent(new ServiceAccessIdRequestRejectedReason(e.getMessage())));

	} catch (PeerRequestHandlingException e) {
	    peerContext.getNodeContext().getLogger().error("Auth error", e);
	    peerManager.handleError(e);
	}

    }

}
