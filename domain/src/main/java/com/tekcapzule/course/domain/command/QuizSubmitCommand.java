package com.tekcapzule.course.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class QuizSubmitCommand extends Command {
    private String courseId;
    private String quizId;
    private List<UserAnswer> userAnswers;

    @Data
    public class UserAnswer {
        private String questionId;
        private List<String> selectedAnswers;
    }
}