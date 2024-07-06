package com.tekcapzule.course.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {
    private int score;
    private boolean isPassed;
    private List<QuizResult.AnswersFeedback> feedbacks;

    @Data
    @Builder
    public static class AnswersFeedback {
        private String questionId;
        private List<String> selectedAnswers;
        private List<String> correctAnswers;
    }
}
