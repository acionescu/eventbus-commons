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
package net.segoia.event.eventbus.peers.comm;

import net.segoia.event.eventbus.peers.security.CommDataContext;
import net.segoia.event.eventbus.peers.security.CommManager;
import net.segoia.event.eventbus.peers.security.CommOperationException;

public class PeerCommManager implements CommManager {
    public static final String DIRECT_COMM="DIRECT";
    public static final String SESSION_COMM="SESSION";
    
    private CommManager directCommManager;
    private CommManager sessionCommManager;

    @Override
    public CommDataContext processsOutgoingData(CommDataContext context) throws CommOperationException {
	return directCommManager.processsOutgoingData(context);
    }

    @Override
    public CommDataContext processIncomingData(CommDataContext context) throws CommOperationException {
	return directCommManager.processIncomingData(context);
    }

    
    public CommDataContext processsOutgoingSessionData(CommDataContext context) throws CommOperationException {
	return sessionCommManager.processsOutgoingData(context);
    }

    public CommDataContext processIncomingSessionData(CommDataContext context) throws CommOperationException {
	return sessionCommManager.processIncomingData(context);
    }

    public CommManager getDirectCommManager() {
        return directCommManager;
    }

    public void setDirectCommManager(CommManager directCommManager) {
        this.directCommManager = directCommManager;
    }

    public CommManager getSessionCommManager() {
        return sessionCommManager;
    }

    public void setSessionCommManager(CommManager sessionCommManager) {
        this.sessionCommManager = sessionCommManager;
    }
    
    
}
