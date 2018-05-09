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

import java.util.HashMap;
import java.util.Map;

import net.segoia.event.eventbus.peers.vo.comm.CommOperationDef;

public class CommManagerConfig {
    private Map<String, CommOperation> txOperations=new HashMap<>();
    private Map<String, CommOperationContextBuilder> txOpContextBuilders= new HashMap<>();
    
    private Map<String, CommOperation> rxOperations=new HashMap<>();
    private Map<String, CommOperationContextBuilder> rxOpContextBuilders = new HashMap<>();
    
    public <O extends CommOperation<?,?,?>> O getTxOperation(CommOperationDef def) {
	return (O)txOperations.get(def.getType());
    }
    
    public CommOperationContextBuilder getTxOpContextBuilder(CommOperationDef def) {
	return txOpContextBuilders.get(def.getType());
    }
    
    public <O extends CommOperation> O getRxOperation(CommOperationDef def) {
	return (O)rxOperations.get(def.getType());
    }
    
    public CommOperationContextBuilder getRxOpContextBuilder(CommOperationDef def) {
	return rxOpContextBuilders.get(def.getType());
    }
    
    public  void addTxOperation(String type, CommOperation op) {
	txOperations.put(type, op);
    }
    
    public void addTxOpContextBuilder(String type, CommOperationContextBuilder builder) {
	txOpContextBuilders.put(type, builder);
    }
    
    public void addRxOperation(String type, CommOperation op) {
	rxOperations.put(type, op);
    }
    
    public void addRxOpContextBuilder(String type, CommOperationContextBuilder builder) {
	rxOpContextBuilders.put(type, builder);
    }
}
