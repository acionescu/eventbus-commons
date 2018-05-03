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
package net.segoia.event.eventbus.peers.vo.comm;

/**
 * Defines the communication protocol between two nodes
 * 
 * @author adi
 *
 */
public class CommunicationProtocol {
    private CommunicationProtocolDefinition protocolDefinition;
    private CommunicationProtocolConfig config;

    public CommunicationProtocol() {
	super();
    }

    public CommunicationProtocol(CommunicationProtocolDefinition protocolDefinition,
	    CommunicationProtocolConfig config) {
	super();
	this.protocolDefinition = protocolDefinition;
	this.config = config;
    }
    
    public static CommunicationProtocol buildPlainProtocol() {
	CommunicationProtocolDefinition def = new CommunicationProtocolDefinition();
	def.setClientCommStrategy(new NodeCommunicationStrategy());
	def.setServerCommStrategy(new NodeCommunicationStrategy());

	CommunicationProtocolConfig config = new CommunicationProtocolConfig();

	return new CommunicationProtocol(def, config);
    }

    public CommunicationProtocolDefinition getProtocolDefinition() {
	return protocolDefinition;
    }

    public void setProtocolDefinition(CommunicationProtocolDefinition protocolDefinition) {
	this.protocolDefinition = protocolDefinition;
    }

    public CommunicationProtocolConfig getConfig() {
	return config;
    }

    public void setConfig(CommunicationProtocolConfig config) {
	this.config = config;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((config == null) ? 0 : config.hashCode());
	result = prime * result + ((protocolDefinition == null) ? 0 : protocolDefinition.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CommunicationProtocol other = (CommunicationProtocol) obj;
	if (config == null) {
	    if (other.config != null)
		return false;
	} else if (!config.equals(other.config))
	    return false;
	if (protocolDefinition == null) {
	    if (other.protocolDefinition != null)
		return false;
	} else if (!protocolDefinition.equals(other.protocolDefinition))
	    return false;
	return true;
    }

}
