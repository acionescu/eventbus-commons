package net.segoia.event.eventbus.peers.util;

import net.segoia.event.eventbus.peers.security.rules.StringMatcher;

public interface StringHelper {
    public StringMatcher buildStringMatcher(String pattern);
}
