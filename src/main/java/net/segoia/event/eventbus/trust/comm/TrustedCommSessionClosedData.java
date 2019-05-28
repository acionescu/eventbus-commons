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
package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionClosedData {
    private String reasonId;
    /* useful when the peer rejects request before a session id is allocated */
    private String linkKeyId;

    public TrustedCommSessionClosedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public TrustedCommSessionClosedData(String reasonId) {
	super();
	this.reasonId = reasonId;
    }

    public TrustedCommSessionClosedData(String reasonId, String linkKeyId) {
        this.reasonId = reasonId;
        this.linkKeyId = linkKeyId;
    }
    
    

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getLinkKeyId() {
        return linkKeyId;
    }

    public void setLinkKeyId(String linkKeyId) {
        this.linkKeyId = linkKeyId;
    }
    
    
}
