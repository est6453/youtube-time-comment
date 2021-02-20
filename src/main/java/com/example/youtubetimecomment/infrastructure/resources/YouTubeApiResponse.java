package com.example.youtubetimecomment.infrastructure.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class YouTubeApiResponse {
    @JsonProperty("kind")
    private String kind;

    @JsonProperty("etag")
    private String etag;

    @JsonProperty("nextPageToken")
    private String nextPageToken;

    @JsonProperty("pageInfo")
    private PageInfo pageInfo;

    @JsonProperty("items")
    private List<Item> items;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class PageInfo {
        @JsonProperty("totalResults")
        private Integer totalResults;

        @JsonProperty("resultsPerPage")
        private Integer resultsPerPage;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Item {
        @JsonProperty("kind")
        private String kind;

        @JsonProperty("etag")
        private String etag;

        @JsonProperty("id")
        private String id;

        @JsonProperty("snippet")
        private Snippet snippet;

        @JsonProperty("canReply")
        private Boolean canReply;

        @JsonProperty("totalReplyCount")
        private Integer totalReplyCount;

        @JsonProperty("isPublic")
        private Boolean isPublic;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Snippet {
        @JsonProperty("videoId")
        private String videoId;

        @JsonProperty("topLevelComment")
        private TopLevelComment topLevelComment;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class AuthorChannelId {
        @JsonProperty("value")
        private String value;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class TopLevelComment {
        @JsonProperty("kind")
        private String kind;

        @JsonProperty("etag")
        private String etag;

        @JsonProperty("id")
        private String id;

        @JsonProperty("snippet")
        private SnippetInTopLevelComment snippetInTopLevelComment;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SnippetInTopLevelComment {
        @JsonProperty("videoId")
        private String videoId;

        @JsonProperty("textDisplay")
        private String textDisplay;

        @JsonProperty("textOriginal")
        private String textOriginal;

        @JsonProperty("authorDisplayName")
        private String authorDisplayName;

        @JsonProperty("authorProfileImageUrl")
        private String authorProfileImageUrl;

        @JsonProperty("authorChannelUrl")
        private String authorChannelUrl;

        @JsonProperty("authorChannelId")
        private AuthorChannelId authorChannelId;

        @JsonProperty("canRate")
        private Boolean canRate;

        @JsonProperty("viewerRating")
        private String viewerRating;

        @JsonProperty("likeCount")
        private Integer likeCount;

        @JsonProperty("publishedAt")
        private Date publishedAt;

        @JsonProperty("updatedAt")
        private Date updatedAt;
    }
}
