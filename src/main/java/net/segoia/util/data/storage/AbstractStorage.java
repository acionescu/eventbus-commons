package net.segoia.util.data.storage;

public abstract class AbstractStorage implements Storage {
    /**
     * The parent storage. Null if none
     */
    private Storage parent;

    public AbstractStorage() {
	super();
    }

    public AbstractStorage(Storage parent) {
	super();
	this.parent = parent;
    }

    public Storage getParent() {
	return parent;
    }

}
