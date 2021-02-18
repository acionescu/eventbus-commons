package net.segoia.util.data.storage;

public class GenericStorageFilter implements StorageFilter {
    public StorageFilterData filterData;

    public GenericStorageFilter(StorageFilterData filterData) {
	super();
	this.filterData = filterData;
    }

    @Override
    public boolean test(StorageEntityInfo input) {
	boolean accepted = !filterData.isDirectoryOnly() || input.isDirectory();
	
	return accepted;
    }

}
