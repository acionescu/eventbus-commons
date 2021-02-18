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
package net.segoia.util.data.storage;

public class DataUnit {
    private String name;

    private String shortDesc;
    /**
     * How many bytes does this data unit contains
     */
    private long bytesCount;

    private DataQuantity baseQuantity;

    private DataUnit(String name, String shortDesc, int bytesCount) {
	super();
	this.name = name;
	this.shortDesc = shortDesc;
	this.bytesCount = bytesCount;
    }

    public DataUnit(String name, String shortDesc, DataQuantity baseQuantity) {
	super();
	this.name = name;
	this.shortDesc = shortDesc;
	this.baseQuantity = baseQuantity;
	this.bytesCount = (long) baseQuantity.getAmount() * baseQuantity.getUnit().bytesCount;
    }

    public String getName() {
	return name;
    }

    public String getShortDesc() {
	return shortDesc;
    }

    public long getBytesCount() {
	return bytesCount;
    }

    public DataQuantity getBaseQuantity() {
	return baseQuantity;
    }

    public static DataUnit BYTE = new DataUnit("Byte", "B", 1);
    public static DataUnit KILOBYTE = new DataUnit("KiloByte", "KB", new DataQuantity(1024, BYTE));
    public static DataUnit MEGABYTE = new DataUnit("MegaByte", "MB", new DataQuantity(1024, KILOBYTE));
    public static DataUnit GIBABYTE = new DataUnit("GigaByte", "GB", new DataQuantity(1024, MEGABYTE));
    public static DataUnit TERABYTE = new DataUnit("TeraByte", "TB", new DataQuantity(1024, GIBABYTE));
}
