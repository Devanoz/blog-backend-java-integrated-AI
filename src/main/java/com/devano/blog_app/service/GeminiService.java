package com.devano.blog_app.service;

import com.devano.blog_app.response.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:gemini.properties")
public class GeminiService {
    @Value("${gemini.api-key}")
    private String API_KEY;
    @Value("${gemini.url}")
    private String geminiUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<GeminiResponse> generatePost(String postTitle) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestText = "Create simple article with title %s".formatted(postTitle);
        String jsonBody = """
                {
                  "contents": [{
                    "parts":[{"text": "%s"}]
                    }]
                   }
                """.formatted(requestText);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        Map<String,String> params = new HashMap<>();
        params.put("key",API_KEY);
        return restTemplate.postForEntity(geminiUrl,entity.getBody(), GeminiResponse.class,params);
    }


}
