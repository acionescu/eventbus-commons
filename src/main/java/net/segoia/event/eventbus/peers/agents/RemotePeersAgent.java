package net.segoia.event.eventbus.peers.agents;


import net.segoia.event.conditions.StrictChannelMatchCondition;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.peers.PeersManagerAgent;

public class RemotePeersAgent extends PeersManagerAgent{
    

    @Override
    protected void config() {
	System.out.println("RemotePeersAgent init");
	
    }

    @Override
    protected void registerHandlers() {
	
	FilteringEventProcessor processor = new FilteringEventProcessor();
	
	context.registerPeerEventProcessor(new StrictChannelMatchCondition("WSS_WEB_V0"), (c) ->{
	    processor.processEvent(c);
	}); 
	
	
	processor.addEventHandler((c) -> {
	    context.logger().info("remote agent got event: "+c.getEvent().toJson());
	});
	
    }

}
