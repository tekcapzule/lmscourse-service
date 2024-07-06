package com.tekcapzule.course.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.course.domain.command.QuizSubmitCommand;
import com.tekcapzule.course.domain.model.Module;
import com.tekcapzule.course.domain.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class QuizSubmissionInput {
    private String courseId;
    private String quizId;
    private List<UserAnswer> userAnswers;

    private class UserAnswer {
        private String questionId;
        private String selectedAnswer;
    }
}