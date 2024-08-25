package com.tekcapzule.course.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.course.application.config.AppConfig;
import com.tekcapzule.course.application.function.input.CreateQuizInput;
import com.tekcapzule.course.application.mapper.InputOutputMapper;
import com.tekcapzule.course.domain.command.CreateQuizCommand;
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
public class CreateQuizFunction implements Function<Message<CreateQuizInput>, Message<Void>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public CreateQuizFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateQuizInput> createQuizInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload;
        String stage = appConfig.getStage().toUpperCase();

        try {
            CreateQuizInput createQuizInput = createQuizInputMessage.getPayload();
            log.info(String.format("Entering create Quiz Function - course ID:%s", createQuizInput.getCourseId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createQuizInputMessage.getHeaders());
            CreateQuizCommand createQuizCommand = InputOutputMapper.buildCreateCommandFromCreateQuizInput.apply(createQuizInput, origin);
            courseService.createQuiz(createQuizCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}