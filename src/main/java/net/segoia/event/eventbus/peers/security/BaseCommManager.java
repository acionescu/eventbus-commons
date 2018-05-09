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

import java.util.ArrayList;
import java.util.List;

import net.segoia.event.eventbus.peers.vo.comm.CommOperationDef;
import net.segoia.event.eventbus.peers.vo.comm.CommStrategy;

public class BaseCommManager<C extends CommProtocolContext> implements CommManager {
    private C commProtocolContext;
    private CommManagerConfig config;

    private SequentialOperationsProcessor txOpProcessor;
    private SequentialOperationsProcessor rxOpProcessor;

    public BaseCommManager(C commProtocolContext, CommManagerConfig config) {
	super();
	this.commProtocolContext = commProtocolContext;
	this.config = config;
	
	init();
    }

    public void init() {

	/* build sequential processor for tx operations */
	List<CommOperationDef> txOpDef = getTxStrategy().getOperations();

	List<OperationExecutionContext> txOpExecContexts = new ArrayList<>();

	for (int i = 0; i < txOpDef.size(); i++) {
	    CommOperationDef cDef = txOpDef.get(i);
	    OperationExecutionContext oec = new OperationExecutionContext(config.getTxOperation(cDef),
		    config.getTxOpContextBuilder(cDef).buildContext(cDef, commProtocolContext));

	    txOpExecContexts.add(oec);
	}

	txOpProcessor = new SequentialOperationsProcessor(txOpExecContexts);

	/* build sequential processor for rx operations */
	List<CommOperationDef> rxOpDef = getRxStrategy().getOperations();
	List<OperationExecutionContext> rxOpExecContexts = new ArrayList<>();

	/* the rx operations should be processed in reverse order */
	for (int i = rxOpDef.size()-1; i >=0; i--) {
	    CommOperationDef cDef = rxOpDef.get(i);
	    OperationExecutionContext oec = new OperationExecutionContext(config.getRxOperation(cDef),
		    config.getRxOpContextBuilder(cDef).buildContext(cDef, commProtocolContext));

	    rxOpExecContexts.add(oec);
	}

	rxOpProcessor = new SequentialOperationsProcessor(rxOpExecContexts);

    }

    public CommStrategy getTxStrategy() {
	return commProtocolContext.getTxStrategy();
    }

    public CommStrategy getRxStrategy() {
	return commProtocolContext.getRxStrategy();
    }

    public C getCommProtocolContext() {
	return commProtocolContext;
    }

    @Override
    public CommDataContext processsOutgoingData(CommDataContext context) throws CommOperationException {
	try {
	    return txOpProcessor.processs(context);
	} catch (GenericOperationException e) {
	    throw new CommOperationException(e);
	}
    }

    @Override
    public CommDataContext processIncomingData(CommDataContext context) throws CommOperationException {
	try {
	    return rxOpProcessor.processs(context);
	} catch (GenericOperationException e) {
	    throw new CommOperationException(e);
	}
    }

}
