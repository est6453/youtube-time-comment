package com.example.youtubetimecomment.infrastructure;

import com.example.youtubetimecomment.infrastructure.resources.YouTubeApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class YouTubeApiClientTest {
    private YouTubeApiClient target;
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    private static final String YOUTUBE_API_URL = "https://www.googleapis.com";
    private static final String TEST_KEY = "testKey";

    private static final String GET_COMMENTS_PATH = "/youtube/v3/commentThreads";
    private static final String PART_VALUE = "snippet";
    private static final String TEST_VIDEO_ID = "testVideoId";

    @BeforeEach
    void setUp() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplate = restTemplateBuilder.build();
        server = MockRestServiceServer.bindTo(restTemplate).build();

        target = new YouTubeApiClient(restTemplate, YOUTUBE_API_URL, TEST_KEY);
    }

    @Test
    @DisplayName("getComment: 正常系")
    void getCommentNormal() {
        String expectedJson = createExpectedJson();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(YOUTUBE_API_URL + GET_COMMENTS_PATH)
                .queryParam("key", TEST_KEY)
                .queryParam("part", PART_VALUE)
                .queryParam("videoId", TEST_VIDEO_ID);

        server.expect(
                ExpectedCount.once(),
                requestTo(builder.toUriString()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedJson, MediaType.APPLICATION_JSON));

        // TODO: YouTubeApiResponse の parse がうまくいったみたい
        YouTubeApiResponse actual = target.getComments(TEST_VIDEO_ID);
        YouTubeApiResponse expected = createResponse();

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * 期待値の Json を生成する
     * 実際に curl で叩いた結果を元に作成している。
     */
    private String createExpectedJson() {
        return  "{\n" +
                "  \"kind\": \"youtube#commentThreadListResponse\",\n" +
                "  \"etag\": \"pdIXZ8sOqfuWg60WNp4tP9379N0\",\n" +
                "  \"nextPageToken\": \"QURTSl9pM0toRFpSXzRXNnRlM1czOGRULXZmS3FVMHh3X2pxT0hWTjkzTERxeWVwbTl3a0NRMmN6ZmxpTkZ3UEtOVl9EVFhCSVFkOUZPR3lDdWFRVHZZaVpJdWlBdUtpd3BycHBtT0Q2MjVHN25ZYzJhNWFPYzBrSTN1c1RuSEdVdmE1a2FWclk4WTZjRm1uWXpRUGVJajl1Z1RsVmF2b2NUempjUEhjaF9yNGxnYnRUdVFUcUxheF9aZl9JdUhUS0ZqTmJCS1ZTTld6Ulk1THVEcFc2eldEX2E5RU9CdTQxdlhYNWZSSTlJMngwNlNpOUR1TEVZemxfa1R2LVN4ZG8ydjJUd1oteUxLRjQ4MTN1V1hyM3JxRzhOYWJPU0E3WFZaSGJ4RkhIMUJnR21GeDlqVlJUUVh5WjFYcUJEc2VSV0MtRFo5UjhrWlZTazBpQ3BFTzdDUGV5OFBVSjktV2hBX0tQenhzeXZOMmJ6ODljVm1CeDZ3eXZaNGxteW00VmtZYWJ2aUQ2dWhEQUpwOXFSeWpQMWlJdTgwSWx2LWEwbThEdEZSZEpjbVlaeFFIb0c2MnpxU3VkdlZtUmkyYVVZMGxoQmNX\",\n" +
                "  \"pageInfo\": {\n" +
                "    \"totalResults\": 100,\n" +
                "    \"resultsPerPage\": 100\n" +
                "  },\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"kind\": \"youtube#commentThread\",\n" +
                "      \"etag\": \"T5iM8LGXAn9e5IjXN8EUuplpm9k\",\n" +
                "      \"id\": \"UgyVr0uahGQ6vHEuIO94AaABAg\",\n" +
                "      \"snippet\": {\n" +
                "        \"videoId\": \"CHMKC1R0a7s\",\n" +
                "        \"topLevelComment\": {\n" +
                "          \"kind\": \"youtube#comment\",\n" +
                "          \"etag\": \"v_zAdquXgsec8I3RvIIp__lT_mI\",\n" +
                "          \"id\": \"UgyVr0uahGQ6vHEuIO94AaABAg\",\n" +
                "          \"snippet\": {\n" +
                "            \"videoId\": \"CHMKC1R0a7s\",\n" +
                "            \"textDisplay\": \"きょうはどんな一日だった？　よかった？　だめだった？\\u003cbr /\\u003eもうよるもおそいから　ゆっくりおやすみ。\\u003cbr /\\u003eまたあしたもがんばろう。　ガチャン、ツーツーツー\",\n" +
                "            \"textOriginal\": \"きょうはどんな一日だった？　よかった？　だめだった？\\nもうよるもおそいから　ゆっくりおやすみ。\\nまたあしたもがんばろう。　ガチャン、ツーツーツー\",\n" +
                "            \"authorDisplayName\": \"jute soymote\",\n" +
                "            \"authorProfileImageUrl\": \"https://yt3.ggpht.com/ytc/AAUvwnjIPiM9gEnUi440eR8lRw40-lUb4hVxo9I4JQ=s48-c-k-c0xffffffff-no-rj-mo\",\n" +
                "            \"authorChannelUrl\": \"http://www.youtube.com/channel/UCZ8no_I1l4d70Oxtul2zmGg\",\n" +
                "            \"authorChannelId\": {\n" +
                "              \"value\": \"UCZ8no_I1l4d70Oxtul2zmGg\"\n" +
                "            },\n" +
                "            \"canRate\": true,\n" +
                "            \"viewerRating\": \"none\",\n" +
                "            \"likeCount\": 197,\n" +
                "            \"publishedAt\": \"2018-07-19T14:58:58Z\",\n" +
                "            \"updatedAt\": \"2018-07-19T14:58:58Z\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"canReply\": true,\n" +
                "        \"totalReplyCount\": 1,\n" +
                "        \"isPublic\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"kind\": \"youtube#commentThread\",\n" +
                "      \"etag\": \"TPwdmAfIHpCbLZH7M-LGL0n7JlU\",\n" +
                "      \"id\": \"UgxQeA0kqS3WtZbABtJ4AaABAg\",\n" +
                "      \"snippet\": {\n" +
                "        \"videoId\": \"CHMKC1R0a7s\",\n" +
                "        \"topLevelComment\": {\n" +
                "          \"kind\": \"youtube#comment\",\n" +
                "          \"etag\": \"XTFIVpwus6VLg-cN7URG6gDCU80\",\n" +
                "          \"id\": \"UgxQeA0kqS3WtZbABtJ4AaABAg\",\n" +
                "          \"snippet\": {\n" +
                "            \"videoId\": \"CHMKC1R0a7s\",\n" +
                "            \"textDisplay\": \"Switchでソフト出たら即買いやのになー！\",\n" +
                "            \"textOriginal\": \"Switchでソフト出たら即買いやのになー！\",\n" +
                "            \"authorDisplayName\": \"えだくん\",\n" +
                "            \"authorProfileImageUrl\": \"https://yt3.ggpht.com/ytc/AAUvwnioB2sUBYRNwfvXg9Yog0b_afKGg3BaAllGOPu4=s48-c-k-c0xffffffff-no-rj-mo\",\n" +
                "            \"authorChannelUrl\": \"http://www.youtube.com/channel/UC7uD2Pef5b4hlbwE4J6nFVA\",\n" +
                "            \"authorChannelId\": {\n" +
                "              \"value\": \"UC7uD2Pef5b4hlbwE4J6nFVA\"\n" +
                "            },\n" +
                "            \"canRate\": true,\n" +
                "            \"viewerRating\": \"none\",\n" +
                "            \"likeCount\": 271,\n" +
                "            \"publishedAt\": \"2018-05-20T06:00:23Z\",\n" +
                "            \"updatedAt\": \"2018-05-20T06:00:23Z\"\n" +
                "          }\n" +
                "        },\n" +
                "      \"canReply\": true,\n" +
                "        \"totalReplyCount\": 14,\n" +
                "        \"isPublic\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private YouTubeApiResponse createResponse() {
        // TODO: レスポンスオブジェクトを上記の JSON の値を元に Builder を使って作る
        return null;
    }

}
