package net.segoia.event.eventbus.peers.util;

public interface EventNodeLogger {

    void debug(Object msg);

    void info(Object msg);

    void error(Object msg, Throwable t);

    void error(Object msg);

    boolean isDebugEnabled();

}
