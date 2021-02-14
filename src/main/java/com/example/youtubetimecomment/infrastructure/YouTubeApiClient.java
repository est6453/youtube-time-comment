package com.example.youtubetimecomment.infrastructure;

import com.example.youtubetimecomment.infrastructure.resources.YouTubeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
public class YouTubeApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String key;

    private static final String GET_COMMENTS_PATH = "/youtube/v3/commentThreads";
    private static final String PART_VALUE = "snippet";

    /**
     * YouTube API を叩き、videoId をキーにして、コメントを取得する
     */
    public YouTubeApiResponse getComments(String videoId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + GET_COMMENTS_PATH)
                .queryParam("key", key)
                .queryParam("part", PART_VALUE)
                .queryParam("videoId", videoId);

        return restTemplate.getForObject(builder.toUriString(), YouTubeApiResponse.class);
    }
}
