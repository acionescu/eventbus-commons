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
package net.segoia.event.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.segoia.event.eventbus.vo.security.DataAuthLevel;

/**
 * This defines the header of an {@link Event} </br>
 * Contains a map of parameters and a set of tags
 * 
 * @author adi
 *
 */
public class EventHeader implements Cloneable {
    private Map<String, Object> params;
    private List<String> tags;

    /**
     * Hold the id of the event that caused this event to be triggered
     */
    private String causeEventId;

    /**
     * The reference to the event that caused this event
     */
    private transient Event causeEvent;

    /**
     * Holds the ids of the events that were triggered by this event
     */
    private transient Set<String> spawnedEventsIds;

    /**
     * The id of the entity that generated this event ( the first relay )
     */
    private String from;

    /**
     * Each time this event is forwarded to another bus, the relaying bus id is added here
     */
    private List<String> relayedBy = new ArrayList<>();

    /**
     * The event should only be relayed to the node with this address
     */
    private String to;

    /**
     * The event should be forwarded to these nodes
     */
    private List<String> forwardTo = new ArrayList<>();
    
    /**
     *  The event should not be forwarded to these peers
     */
    private List<String> noForward;

    /**
     * Use this to mark the event as handled
     */
    private transient boolean handled;

    /**
     * Unique key to identify the event's source agent
     */
    private transient String sourceAgentId;

    /**
     * The main identity of the source agent
     */
    private transient String rootAgentId;

    /**
     * The channel through which this event entered our system
     */
    private String channel;

    /**
     * The authentication level for this event <br>
     * This is transient so that auth levels can't be injected from peers
     * 
     */
    private transient DataAuthLevel authLevel;

    /**
     * The id of a specific communication session
     */
    private String commSessionId;

    /**
     * The custom id of the original source of this event
     */
    private String originId;

    /**
     * A custom alias for the source of this event
     */
    private transient String sourceAlias;
    
    /**
     * Type of event's source
     */
    private transient String sourceType;
    
    /**
     * This is a way to exchange root cause events between nodes
     */
    private Event originRootCause;

    public EventHeader() {
	params = new HashMap<>();
	tags = new ArrayList<>();
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see java.lang.Object#clone()
    // */
    // protected EventHeader clone() {
    //
    // try {
    // EventHeader c = (EventHeader) super.clone();
    //
    // /* do a shallow copy for these */
    // c.params = new HashMap<>(params);
    // c.tags = new ArrayList<String>(tags);
    // c.relayedBy = new ArrayList<String>(relayedBy);
    // c.spawnedEventsIds = new LinkedHashSet<>(spawnedEventsIds);
    //
    // return c;
    // } catch (CloneNotSupportedException e) {
    // e.printStackTrace();
    // return null;
    // }
    //
    // }

    public EventHeader addParam(String key, Object value) {
	params.put(key, value);
	return this;
    }

    public EventHeader addTag(String tag) {
	if (tags == null) {
	    tags = new ArrayList<>();
	}
	if (!tags.contains(tag)) {
	    tags.add(tag);
	}
	return this;
    }

    public Object getParam(String key) {
	return params.get(key);
    }

    public boolean hasTag(String tag) {
	return tags.contains(tag);
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
	return params;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
	return tags;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(Map<String, Object> params) {
	this.params = params;
    }

    /**
     * @return the causeEventId
     */
    public String getCauseEventId() {
	return causeEventId;
    }

    /**
     * @param causeEventId
     *            the causeEventId to set
     */
    public void setCauseEventId(String causeEventId) {
	this.causeEventId = causeEventId;
    }

    public void addSpawnedEventId(String id) {
	if (spawnedEventsIds == null) {
	    spawnedEventsIds = new LinkedHashSet<>();
	}
	spawnedEventsIds.add(id);
    }

    /**
     * @return the spawnedEventsIds
     */
    public Set<String> getSpawnedEventsIds() {

	return spawnedEventsIds;
    }

    public void addRelay(String busNodeId) {
	relayedBy.add(busNodeId);
	if (from == null) {
	    from = busNodeId;
	}
    }

    /**
     * @return the sourceBusId
     */
    public String from() {
	return from;
    }

    public boolean wasRelayedBy(String busNodeId) {
	return relayedBy.contains(busNodeId);
    }

    public int relayHops() {
	return relayedBy.size();
    }

    public void to(String nodeId) {
	this.to = nodeId;
    }

    public String to() {
	return this.to;
    }

    /**
     * @return the relayedBy
     */
    public List<String> getRelayedBy() {
	return relayedBy;
    }

    public void replaceRelay(String oldId, String newId) {
	int i = relayedBy.indexOf(oldId);
	if (i >= 0) {
	    relayedBy.set(i, newId);
	    if (i == 0) {
		from = newId;
	    }
	}
    }

    public void removeLastRelay() {
	int li = relayedBy.size() - 1;
	if (li >= 0) {
	    relayedBy.remove(li);
	    if (li == 0) {
		from = null;
	    }
	}
    }

    public String getLastRelay() {

	int size = relayedBy.size();
	if (size > 0) {
	    return relayedBy.get(size - 1);
	}
	return null;
    }

    public void clearRelays() {
	relayedBy.clear();
	from = null;
    }
    
    public void removeRelay(String rid) {
	int i = relayedBy.indexOf(rid);
	if(i >=0 ) {
	    relayedBy.remove(i);
	    if(i == 0) {
		from=null;
	    }
	}
    }

    /**
     * @return the forwardTo
     */
    public List<String> getForwardTo() {
	return forwardTo;
    }

    public void clearForwardTo() {
	if(forwardTo != null) {
	    forwardTo.clear();
	}
    }

    public List<String> getNoForward() {
        return noForward;
    }
    
    public void clearNoForward() {
	noForward=null;
    }
    
    public boolean isNoForward(String peerId) {
	return (noForward !=null && noForward.contains(peerId));
    }

    /**
     * @param forwardTo
     *            the forwardTo to set
     */
    public void setForwardTo(List<String> forwardTo) {
	this.forwardTo = forwardTo;
    }

    public void addForwardTo(String nodeId) {
	if (forwardTo == null) {
	    forwardTo = new ArrayList<>();
	}
	if (!forwardTo.contains(nodeId)) {
	    forwardTo.add(nodeId);
	}
    }
    
    public void addNoForward(String nodeId) {
	if(noForward == null) {
	    noForward=new ArrayList<>();
	}
	if(!noForward.contains(nodeId)) {
	    noForward.add(nodeId);
	}
    }

    public void setHandled() {
	this.handled = true;
    }

    /**
     * @return the handled
     */
    public boolean isHandled() {
	return handled;
    }

    public void setTo(String to) {
	this.to = to;
    }

    /**
     * @return the causeEvent
     */
    public Event getCauseEvent() {
	return causeEvent;
    }

    /**
     * @param causeEvent
     *            the causeEvent to set
     */
    public void setCauseEvent(Event causeEvent) {
	this.causeEvent = causeEvent;
	if (causeEvent != null) {
	    setCauseEventId(causeEvent.getId());
	}
    }

    public String getFrom() {
	return from;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public String getTo() {
	return to;
    }

    public void setTags(List<String> tags) {
	this.tags = tags;
    }

    public void setSpawnedEventsIds(Set<String> spawnedEventsIds) {
	this.spawnedEventsIds = spawnedEventsIds;
    }

    public void setRelayedBy(List<String> relayedBy) {
	this.relayedBy = relayedBy;
    }

    public void setHandled(boolean handled) {
	this.handled = handled;
    }

    public String getSourceAgentId() {
	return sourceAgentId;
    }

    public void setSourceAgentId(String sourceAgentId) {
	this.sourceAgentId = sourceAgentId;
    }

    public String getRootAgentId() {
	return rootAgentId;
    }

    public void setRootAgentId(String rootAgentId) {
	this.rootAgentId = rootAgentId;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    public DataAuthLevel getAuthLevel() {
	return authLevel;
    }

    public void setAuthLevel(DataAuthLevel authLevel) {
	this.authLevel = authLevel;
    }

    public String getCommSessionId() {
	return commSessionId;
    }

    public void setCommSessionId(String commSessionId) {
	this.commSessionId = commSessionId;
    }

    public String getOriginId() {
	return originId;
    }

    public void setOriginId(String originId) {
	this.originId = originId;
    }

    public Event getRootEvent() {
	if (causeEvent != null) {
	    Event r = causeEvent.getHeader().getRootEvent();
	    if (r != null) {
		return r;
	    } else {
		return causeEvent;
	    }
	}
	return null;
    }

    public String getSourceAlias() {
	return sourceAlias;
    }

    public void setSourceAlias(String sourceAlias) {
	this.sourceAlias = sourceAlias;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Event getOriginRootCause() {
        return originRootCause;
    }

    public void setOriginRootCause(Event originRootCause) {
        this.originRootCause = originRootCause;
    }

    public boolean isLocal() {
	return from==null && (relayedBy==null || relayedBy.size()==0);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((causeEventId == null) ? 0 : causeEventId.hashCode());
	result = prime * result + ((forwardTo == null) ? 0 : forwardTo.hashCode());
	result = prime * result + ((params == null) ? 0 : params.hashCode());
	result = prime * result + ((relayedBy == null) ? 0 : relayedBy.hashCode());
	result = prime * result + ((from == null) ? 0 : from.hashCode());
	result = prime * result + ((spawnedEventsIds == null) ? 0 : spawnedEventsIds.hashCode());
	result = prime * result + ((tags == null) ? 0 : tags.hashCode());
	result = prime * result + ((to == null) ? 0 : to.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EventHeader other = (EventHeader) obj;
	if (causeEventId == null) {
	    if (other.causeEventId != null)
		return false;
	} else if (!causeEventId.equals(other.causeEventId))
	    return false;
	if (forwardTo == null) {
	    if (other.forwardTo != null)
		return false;
	} else if (!forwardTo.equals(other.forwardTo))
	    return false;
	if (params == null) {
	    if (other.params != null)
		return false;
	} else if (!params.equals(other.params))
	    return false;
	if (relayedBy == null) {
	    if (other.relayedBy != null)
		return false;
	} else if (!relayedBy.equals(other.relayedBy))
	    return false;
	if (from == null) {
	    if (other.from != null)
		return false;
	} else if (!from.equals(other.from))
	    return false;
	if (spawnedEventsIds == null) {
	    if (other.spawnedEventsIds != null)
		return false;
	} else if (!spawnedEventsIds.equals(other.spawnedEventsIds))
	    return false;
	if (tags == null) {
	    if (other.tags != null)
		return false;
	} else if (!tags.equals(other.tags))
	    return false;
	if (to == null) {
	    if (other.to != null)
		return false;
	} else if (!to.equals(other.to))
	    return false;
	return true;
    }

}
