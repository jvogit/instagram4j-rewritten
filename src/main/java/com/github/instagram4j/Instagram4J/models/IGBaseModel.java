package com.github.instagram4j.Instagram4J.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IGBaseModel {
    @JsonAnySetter
    private Map<String, Object> $extra_properties = new HashMap<>();
    

    @JsonAnyGetter
    public Map<String, Object> getExtraProperties() {
        return this.$extra_properties;
    }
    
    @JsonIgnore
    public Object get(String key) {
        return $extra_properties.get(key);
    }
    
    public IGBaseModel put(String key, Object val) {
        this.$extra_properties.put(key, val);
        
        return this;
    }
}
