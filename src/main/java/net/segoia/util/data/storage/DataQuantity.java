package net.segoia.util.data.storage;

/**
 * This is used to measure a quantity of data. ( e.g. bytes, kilobytes, megabytes, gigabytes, etc )
 * @author adi
 *
 */
public class DataQuantity {
    /**
     * How many units are there
     */
    private float amount;
    /**
     * This represents how many bytes a unit has
     */
    private DataUnit unit;
    
    public DataQuantity(float amount, DataUnit unit) {
	super();
	this.amount = amount;
	this.unit = unit;
    }
    
    public void add(int bytesCount) {
	amount= ((float)bytesCount)/unit.getBytesCount();
    }

    public float getAmount() {
        return amount;
    }

    public DataUnit getUnit() {
        return unit;
    }
    
    
}
