package net.segoia.event.eventbus.peers.agents;

import net.segoia.event.eventbus.peers.PeersAgentContext;

public abstract class PeersAgentController {
    protected PeersAgentContext context;

    public PeersAgentController(PeersAgentContext context) {
	this(context, true);
    }

    public PeersAgentController(PeersAgentContext context, boolean autoinit) {
	super();
	this.context = context;
	if (autoinit) {
	    init();
	}
    }

    protected void init() {
	config();
	registerHandlers();
	initController();
    }

    protected abstract void config();

    protected abstract void initController();

    protected abstract void registerHandlers();
}
