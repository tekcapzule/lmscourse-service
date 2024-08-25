package com.tekcapzule.course.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.course.application.config.AppConfig;
import com.tekcapzule.course.application.function.input.GetCourseByTopicInput;
import com.tekcapzule.course.domain.model.LMSCourse;
import com.tekcapzule.course.domain.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetByTopicFunction implements Function<Message<GetCourseByTopicInput>, Message<List<LMSCourse>>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public GetByTopicFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<LMSCourse>> apply(Message<GetCourseByTopicInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<LMSCourse> courses = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetCourseByTopicInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course Function -Topic Code:%s", getInput.getTopicCode()));
            courses = courseService.findAllByTopicCode(getInput.getTopicCode());
            if (courses.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(courses, responseHeaders);
    }
}