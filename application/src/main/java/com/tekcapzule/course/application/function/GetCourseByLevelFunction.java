package com.tekcapsule.course.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.course.application.config.AppConfig;
import com.tekcapsule.course.application.function.input.GetInput;
import com.tekcapsule.course.domain.model.Course;
import com.tekcapsule.course.domain.service.CourseService;
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
public class GetCourseByLevelFunction implements Function<Message<GetCourseByLevelInput>, Message<List<LMSCourse>>> {

    private final CourseService courseService;

    private final AppConfig appConfig;

    public GetFunction(final CourseService courseService, final AppConfig appConfig) {
        this.courseService = courseService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Course>> apply(Message<GetCourseByLevelInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Course> courses = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetCourseByLevelInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course by level Function -Topic Code:%s", getInput.getTopicCode()));
            courses = courseService.findAllByLevel(getInput.getTopicCode(), getInput.getLevel());
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