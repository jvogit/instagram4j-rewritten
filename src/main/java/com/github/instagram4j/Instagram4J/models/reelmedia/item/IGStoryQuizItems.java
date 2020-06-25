package com.github.instagram4j.Instagram4J.models.reelmedia.item;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class IGStoryQuizItems extends IGReelMetadataItem {
    @NonNull
    private String question;
    private int correct_answer;
    @NonNull
    private List<IGOption> options;
    @Builder.Default
    private String text_color = "#ffffff";
    @Builder.Default
    private String start_background_color = "#262626";
    @Builder.Default
    private String end_background_color = "#262626";
    
    @Override
    public String key() {
        return "story_quizs";
    }
    
    @Data
    @Builder
    public static class IGOption {
        private String text;
        @Builder.Default
        private int count = 0;
    }

}
