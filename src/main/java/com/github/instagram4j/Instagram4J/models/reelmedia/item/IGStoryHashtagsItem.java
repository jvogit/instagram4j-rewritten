package com.github.instagram4j.Instagram4J.models.reelmedia.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class IGStoryHashtagsItem extends IGReelMetadataItem {
    @NonNull
    private String tag_name;

    @Override
    public String key() {
        return "story_hashtags";
    }

}
