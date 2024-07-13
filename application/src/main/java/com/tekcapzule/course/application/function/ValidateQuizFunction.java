package com.tekcapzule.course.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.course.application.config.AppConfig;
import com.tekcapzule.course.application.function.input.QuizSubmissionInput;
import com.tekcapzule.course.application.mapper.InputOutputMapper;
import com.tekcapzule.course.domain.command.QuizSubmitCommand;
import com.tekcapzule.course.domain.model.QuizResult;
import com.tekcapzule.course.domain.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class ValidateQuizFunction implements Function<Message<QuizSubmissionInput>, Message<QuizResult>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public ValidateQuizFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<QuizResult> apply(Message<QuizSubmissionInput> quizSubmissionInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload;
        String stage = appConfig.getStage().toUpperCase();
        QuizResult quizResult = new QuizResult();
        try {
            QuizSubmissionInput quizSubmissionInput = quizSubmissionInputMessage.getPayload();
            log.info(String.format("Entering Validate quiz Function -  Course Id:%s", quizSubmissionInput.getCourseId()));
            log.info(String.format("user answers from input", quizSubmissionInput.getUserAnswers()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(quizSubmissionInputMessage.getHeaders());
            QuizSubmitCommand quizSubmitCommand = InputOutputMapper.buildCreateCommandFromSubmitQuizInput.apply(quizSubmissionInput, origin);
            quizResult = courseService.submit(quizSubmitCommand);
            if (quizResult==null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(quizResult, responseHeaders);

    }
}