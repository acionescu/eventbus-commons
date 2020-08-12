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
package net.segoia.event.conditions;

import net.segoia.event.eventbus.EventContext;

public class StrictChannelMatchCondition extends Condition {
    private String channel;

    public StrictChannelMatchCondition(String channel) {
	super(channel);
	this.channel = channel;
    }

    @Override
    public boolean test(EventContext input) {
	return channel.equals(input.getEvent().getHeader().getChannel());
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((channel == null) ? 0 : channel.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	StrictChannelMatchCondition other = (StrictChannelMatchCondition) obj;
	if (channel == null) {
	    if (other.channel != null)
		return false;
	} else if (!channel.equals(other.channel))
	    return false;
	return true;
    }

    
}
