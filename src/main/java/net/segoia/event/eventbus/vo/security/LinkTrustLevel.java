package net.segoia.event.eventbus.vo.security;


public class LinkTrustLevel {
    /**
     * A value from 0 to 100, where 0 is the maximum trust level
     */
    private int level = -1;

    public LinkTrustLevel() {
	super();
	// TODO Auto-generated constructor stub
    }

    public LinkTrustLevel(int level) {
	super();
	this.level = level;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

}
