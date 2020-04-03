package net.segoia.event.eventbus.peers;

public abstract class PeersManagerAgent {
    protected PeersAgentContext context;
    
    public void init(PeersAgentContext context) {
	this.context = context;
	agentInit();
    }
    
    protected void agentInit() {
	config();
	registerHandlers();
    }
    
    protected abstract void config();
    protected abstract void registerHandlers();
}
