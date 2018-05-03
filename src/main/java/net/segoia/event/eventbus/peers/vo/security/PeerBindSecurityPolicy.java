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
package net.segoia.event.eventbus.peers.vo.security;

import java.util.List;

/**
 * Defines a general security policy that governs the communication between a node and a peer
 * @author adi
 *
 */
public class PeerBindSecurityPolicy {
    /**
     * Require a public key from peers
     */
    private boolean spkiRequiredForPeers;

    /**
     * Require a public key from peers, digitally signed by a certificate authority
     */
    private boolean pkiRequiredForPeers;
    
    
    /**
     * The list of algorithms accepted for key generation
     */
    private List<String> acceptedKeyAlgorithms;
    
    /**
     * The list of algorithms accepted for signing
     */
    private List<String> acceptedSignatureAlgorithms;
    

    public boolean isSpkiRequiredForPeers() {
        return spkiRequiredForPeers;
    }

    public void setSpkiRequiredForPeers(boolean spkiRequiredForPeers) {
        this.spkiRequiredForPeers = spkiRequiredForPeers;
    }

    public boolean isPkiRequiredForPeers() {
        return pkiRequiredForPeers;
    }

    public void setPkiRequiredForPeers(boolean pkiRequiredForPeers) {
        this.pkiRequiredForPeers = pkiRequiredForPeers;
    }

    public List<String> getAcceptedKeyAlgorithms() {
        return acceptedKeyAlgorithms;
    }

    public void setAcceptedKeyAlgorithms(List<String> acceptedKeyAlgorithms) {
        this.acceptedKeyAlgorithms = acceptedKeyAlgorithms;
    }

    public List<String> getAcceptedSignatureAlgorithms() {
        return acceptedSignatureAlgorithms;
    }

    public void setAcceptedSignatureAlgorithms(List<String> acceptedSignatureAlgorithms) {
        this.acceptedSignatureAlgorithms = acceptedSignatureAlgorithms;
    }

}
