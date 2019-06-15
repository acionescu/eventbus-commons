package net.segoia.event.eventbus.peers;

import java.util.Map;

public class GenericPeerManagerAbstractFactory implements PeerManagerFactory {
    private PeerManagerFactory defaultFactory;
    private Map<String, PeerManagerFactory> factoriesByChannel;

    public GenericPeerManagerAbstractFactory() {
	super();
	// TODO Auto-generated constructor stub
    }

    public GenericPeerManagerAbstractFactory(PeerManagerFactory defaultFactory,
	    Map<String, PeerManagerFactory> factoriesByChannel) {
	super();
	this.defaultFactory = defaultFactory;
	this.factoriesByChannel = factoriesByChannel;
    }

    @Override
    public PeerManager buildPeerManager(PeerContext peerContext) {
	PeerManagerFactory factory = null;
	
	if(factoriesByChannel != null) {
	    factory = factoriesByChannel.get(peerContext.getTransceiver().getChannel());
	}
	
	if(factory == null) {
	    factory = defaultFactory;
	}
	
	return factory.buildPeerManager(peerContext);
    }

    public PeerManagerFactory getDefaultFactory() {
        return defaultFactory;
    }

    public void setDefaultFactory(PeerManagerFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    public Map<String, PeerManagerFactory> getFactoriesByChannel() {
        return factoriesByChannel;
    }

    public void setFactoriesByChannel(Map<String, PeerManagerFactory> factoriesByChannel) {
        this.factoriesByChannel = factoriesByChannel;
    }
    
}
