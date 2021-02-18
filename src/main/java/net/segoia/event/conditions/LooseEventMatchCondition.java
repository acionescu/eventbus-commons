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
package net.segoia.event.conditions;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;

public class LooseEventMatchCondition extends Condition{
    private String scope;
    private String category;
    private String name;
    
    public LooseEventMatchCondition(String id) {
	super(id);
    }
    

    public static LooseEventMatchCondition build(String scope, String category, String name) {
	StringBuffer idSb = new StringBuffer();
	if(scope != null) {
	    idSb.append(scope);
	}
	idSb.append(Event.etSep);
	if(category != null) {
	    idSb.append(category);
	}
	idSb.append(Event.etSep);
	if(name != null) {
	    idSb.append(name);
	}
	
	LooseEventMatchCondition c = new LooseEventMatchCondition(idSb.toString());
	c.scope = scope;
	c.category=category;
	c.name = name;
	
	return c;
    }
    
    public static LooseEventMatchCondition build(String scope, String category) {
	return build(scope,category,null);
    }
    
    public static LooseEventMatchCondition buildWithCategory(String category) {
	return build(null,category);
    }
    
    public static LooseEventMatchCondition build(String scope) {
	return build(scope, null, null);
    }

    @Override
    public boolean test(EventContext input) {
	Event e = input.getEvent();
	if(scope != null && !scope.equals(e.getScope())) {
	    return false;
	}
	if(category !=null && !category.equals(e.getCategory())) {
	    return false;
	}
	if(name != null && !name.equals(e.getName())) {
	    return false;
	}
	
	return true;
    }


    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }


    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }


    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((category == null) ? 0 : category.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((scope == null) ? 0 : scope.hashCode());
	return result;
    }


    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LooseEventMatchCondition other = (LooseEventMatchCondition) obj;
	if (category == null) {
	    if (other.category != null)
		return false;
	} else if (!category.equals(other.category))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (scope == null) {
	    if (other.scope != null)
		return false;
	} else if (!scope.equals(other.scope))
	    return false;
	return true;
    }
    
    

}
