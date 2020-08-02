package com.github.instagram4j.Instagram4J.requests.friendships;

import com.github.instagram4j.Instagram4J.IGClient;
import com.github.instagram4j.Instagram4J.requests.IGGetRequest;
import com.github.instagram4j.Instagram4J.requests.IGPaginatedRequest;
import com.github.instagram4j.Instagram4J.responses.feed.FeedUsersResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FriendshipsPendingRequest extends IGGetRequest<FeedUsersResponse> implements IGPaginatedRequest<FeedUsersResponse> {
    @Setter
    private String max_id;
    
    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id);
    }
    
    @Override
    public String path() {
        return "friendships/pending/";
    }

    @Override
    public Class<FeedUsersResponse> getResponseType() {
        return FeedUsersResponse.class;
    }

}
