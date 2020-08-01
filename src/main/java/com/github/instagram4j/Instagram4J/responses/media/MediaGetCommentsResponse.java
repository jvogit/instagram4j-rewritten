package com.github.instagram4j.Instagram4J.responses.media;

import java.util.List;

import com.github.instagram4j.Instagram4J.models.media.timeline.Comment;
import com.github.instagram4j.Instagram4J.models.media.timeline.Comment.Caption;
import com.github.instagram4j.Instagram4J.responses.IGPaginatedResponse;

import lombok.Data;

@Data
public class MediaGetCommentsResponse extends IGPaginatedResponse {
    private List<Comment> comments;
    private Caption caption;
    private String next_max_id;
    
    public boolean isMore_available() {
        return next_max_id != null;
    }
}
