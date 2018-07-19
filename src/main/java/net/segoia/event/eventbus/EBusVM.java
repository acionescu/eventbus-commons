/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.event.eventbus;

import net.segoia.event.conditions.Condition;
import net.segoia.event.eventbus.builders.DefaultComponentEventBuilder;
import net.segoia.event.eventbus.peers.EventBusNodeConfig;
import net.segoia.event.eventbus.peers.EventNode;
import net.segoia.event.eventbus.peers.NodeManager;
import net.segoia.event.eventbus.peers.util.EventNodeHelper;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;

public abstract class EBusVM {

    private FilteringEventBus systemBus;
    private String systemBusConfigFile = "ebus.json";
    protected EventNodeHelper helper;
    protected EventNodeLogger logger;

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

    public NodeManager loadNode(EventBusNodeConfig config, boolean init) {
        NodeManager nm = new NodeManager();
        EventNode eventNode = createNode();
        eventNode.setConfig(config);
        nm.setNode(eventNode);
        setUpNewNode(nm);
        if (init) {
            nm.getNode().lazyInit();
        }
        return nm;
    }

    protected abstract EventNode createNode();

    protected void setUpNewNode(NodeManager nm) {
        EventNode node = nm.getNode();
        EventBusNodeConfig config = node.getConfig();
        if (config.getHelper() == null) {
            config.setHelper(helper);
        }
        if (config.getLogger() == null) {
            config.setLogger(logger);
        }
    }

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
     * Builds an event bus with the given dispatcher that will function on the
     * main loop
     *
     * @param eventDispatcher
     * @return
     */
    public abstract FilteringEventBus buildFilteringEventBusOnMainLoop(EventDispatcher eventDispatcher);

    public abstract EventDispatcher buildBlockingEventDispatcher();

    public abstract void processAllFromMainLoopAndStop();

    public abstract void waitToProcessAllOnMainLoop();

    public abstract void waitToProcessAllOnMainLoop(int sleep);

    public FilteringEventDispatcher buildBlockingFilteringEventDispatcher(Condition condition) {
        return new BlockingFilteringEventDispatcher(condition);
    }

    public EventNodeHelper getHelper() {
        return helper;
    }

    public EventNodeLogger getLogger() {
        return logger;
    }

}
