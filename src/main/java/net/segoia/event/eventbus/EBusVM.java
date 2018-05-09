package net.segoia.event.eventbus;

import net.segoia.event.conditions.Condition;
import net.segoia.event.eventbus.builders.DefaultComponentEventBuilder;
import net.segoia.event.eventbus.peers.NodeManager;

public abstract class EBusVM {
    private FilteringEventBus systemBus;
    private String systemBusConfigFile = "ebus.json";

    private static EBusVM instance;
    public static boolean debugEnabled;

    public static void setInstance(EBusVM i) {
	instance = i;
	instance.getSystemBus();
    }

    public static EBusVM getInstance() {
	return instance;
    }

    protected EBusVM() {
	super();
    }

    public abstract FilteringEventBus loadBusFromJsonFile(String filePath);

    public FilteringEventBus getSystemBus() {
	if (systemBus == null) {
	    systemBus = loadBusFromJsonFile(systemBusConfigFile);
	}
	return systemBus;
    }

    public NodeManager loadNode(String file) {
	return loadNode(file, true);
    }

    public abstract NodeManager loadNode(String file, boolean init);

    public InternalEventTracker postSystemEvent(Event event) {
	return getSystemBus().postEvent(event);
    }

    public EventHandle getHandle(Event event) {
	return getSystemBus().getHandle(event);
    }

    public static DefaultComponentEventBuilder getComponentEventBuilder(String componentId) {
	return new DefaultComponentEventBuilder(componentId);
    }

    public abstract FilteringEventBus buildAsyncFilteringEventBus(int cacheCapacity, int workerThreads,
	    EventDispatcher eventDispatcher);

    public FilteringEventBus buildSingleThreadedAsyncFilteringEventBus(int cacheCapacity,
	    EventDispatcher eventDispatcher) {
	return buildAsyncFilteringEventBus(cacheCapacity, 1, eventDispatcher);
    }

    public abstract FilteringEventBus buildSingleThreadedAsyncFilteringEventBus(int cacheCapacity);

    /**
     * Builds an event bus with the given dispatcher that will function on the main loop
     * 
     * @param eventDispatcher
     * @return
     */
    public abstract FilteringEventBus buildFilteringEventBusOnMainLoop(EventDispatcher eventDispatcher);
    
    
    public abstract EventDispatcher buildBlockingEventDispatcher();

    public abstract void processAllFromMainLoopAndStop();

    public abstract void waitToProcessAllOnMainLoop();

    public abstract void waitToProcessAllOnMainLoop(int sleep);
    
    public FilteringEventDispatcher buildBlockingFilteringEventDispatcher(
	    Condition condition) {
	return new BlockingFilteringEventDispatcher(condition);
    }
}
