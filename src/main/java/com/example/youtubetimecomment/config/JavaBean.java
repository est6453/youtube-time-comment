package com.example.youtubetimecomment.config;

import com.example.youtubetimecomment.infrastructure.YouTubeApiClient;
import com.example.youtubetimecomment.infrastructure.YouTubeApiClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class JavaBean {
    /**
     * YouTube API client の Bean 生成クラス
     */
    @Bean
    public YouTubeApiClient youTubeApiClient(YouTubeApiClientProperties youTubeApiClientProperties) {
        return new YouTubeApiClient(new RestTemplate(),
                youTubeApiClientProperties.getUrl(),
                youTubeApiClientProperties.getKey()
        );
    }
}
