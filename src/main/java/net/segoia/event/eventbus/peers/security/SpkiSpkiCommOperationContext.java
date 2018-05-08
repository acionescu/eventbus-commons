package net.segoia.event.eventbus.peers.security;

import net.segoia.event.eventbus.peers.vo.comm.CommOperationDef;

public class SpkiSpkiCommOperationContext<D extends CommOperationDef>
	extends SpkiCommOperationContext<D, SpkiPrivateIdentityManager, SpkiPublicIdentityManager> {
    
   
    public SpkiSpkiCommOperationContext(D opDef, SpkiPrivateIdentityManager ourIdentity,
	    SpkiPublicIdentityManager peerIdentity) {
	super(opDef, ourIdentity, peerIdentity);
    }

    

}
