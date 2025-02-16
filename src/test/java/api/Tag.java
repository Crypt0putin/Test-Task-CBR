package api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
} 