package net.segoia.event.eventbus.app;

public interface EventNodeAppControllerFactory<I,C extends EventNodeAppController<?>> {
    
    public  C createController(I input);

}
