package net.segoia.event.eventbus.peers.security.rules;

public class PeerRuleEngine {
    private RuleMatcherFactory ruleMatcherFactory;

    public PeerEventRule buildRegexRule(String regex) {

	RuleMatcher ruleMatcher = ruleMatcherFactory.buildRegexRuleMatcher(regex);
	PeerEventRule r = new PeerEventRule(ruleMatcher);
	return r;
    }

    public RuleMatcherFactory getRuleMatcherFactory() {
	return ruleMatcherFactory;
    }

    public void setRuleMatcherFactory(RuleMatcherFactory ruleMatcherFactory) {
	this.ruleMatcherFactory = ruleMatcherFactory;
    }

}
