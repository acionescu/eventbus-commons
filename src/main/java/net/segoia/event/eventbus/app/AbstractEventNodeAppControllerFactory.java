package net.segoia.event.eventbus.app;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventNodeAppControllerFactory<I,C extends EventNodeAppController<?>> implements EventNodeAppControllerFactory<I, C>{
    private List<EventNodeAppControllerFactory<I,C>> supportedVersions = new ArrayList<>();;
    
    @Override
    public C createController(I input) {
	EventNodeAppControllerFactory<I, C> factory = getFactoryForInput(input);
	
	if(factory != null) {
	    return factory.createController(input);
	}
	return null;
    }
    
    abstract EventNodeAppControllerFactory<I, C> getFactoryForInput(I input);
    
    public void addFactory(EventNodeAppControllerFactory<I, C> factory) {
	supportedVersions.add(factory);
    }

}
