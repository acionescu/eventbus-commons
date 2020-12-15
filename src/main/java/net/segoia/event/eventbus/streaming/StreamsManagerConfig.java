package net.segoia.event.eventbus.streaming;

import java.util.List;

public class StreamsManagerConfig {
    private List<String> allowedMainTypes;

    public List<String> getAllowedMainTypes() {
        return allowedMainTypes;
    }

    public void setAllowedMainTypes(List<String> allowedMainTypes) {
        this.allowedMainTypes = allowedMainTypes;
    }
    
    
    
}
