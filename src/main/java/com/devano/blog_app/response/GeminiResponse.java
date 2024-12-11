package com.devano.blog_app.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;

    @Setter
    @Getter
    public static class Candidate {
        private Content content;
        private String finishReason;
        private double avgLogprobs;

        @Setter
        @Getter
        public static class Content {
            private List<Part> parts;

            @Setter
            @Getter
            public static class Part {
                private String text;

            }
        }
    }

    @Setter
    @Getter
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;

    }
}
