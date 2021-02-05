package net.segoia.event.eventbus.app;

/**
 * Provides a way for an event node agent to easily manage more controllers
 * @author adi
 *
 */
public class EventNodeControllersManager<I, C extends EventNodeAppController<?>> {
    /**
     * A factory to create controllers
     */
    private EventNodeAppControllerFactory<I, C> controllersFactory;
    
    
}
