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
package net.segoia.event.eventbus.peers;

import java.util.HashSet;
import java.util.Set;

public class FollowersContext {
    private Set<String> followers=new HashSet<>();
    
    
    public boolean addFollower(String followerId) {
	return followers.add(followerId);
    }
    
    public boolean removeFollower(String followerId) {
	return followers.remove(followerId);
    }
    
    public boolean hasFollower(String followerId) {
	return followers.contains(followerId);
    }

    public int followersCount() {
	return followers.size();
    }
    
    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }
    
}
