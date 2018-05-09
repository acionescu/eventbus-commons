package net.segoia.event.eventbus.peers.security.rules;

public interface RuleMatcherFactory {
    
    RuleMatcher buildRegexRuleMatcher(String regex);

}
