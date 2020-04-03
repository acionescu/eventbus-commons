package net.segoia.event.eventbus.peers;

public abstract class PeerManagerAgent {
    protected PeerManagerContext context;
    
    public void init(PeerManagerContext context) {
	this.context=context;
	agentInit();
    }
    
    protected void agentInit() {
	config();
	registerHandlers();
    }
    
    protected abstract void config();
    protected abstract void registerHandlers();
    
}
