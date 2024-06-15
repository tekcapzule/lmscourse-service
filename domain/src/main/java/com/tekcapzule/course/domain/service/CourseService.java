package com.tekcapzule.course.domain.service;

import com.tekcapzule.course.domain.command.ApproveCommand;
import com.tekcapzule.course.domain.command.CreateCommand;
import com.tekcapzule.course.domain.command.RecommendCommand;
import com.tekcapzule.course.domain.command.UpdateCommand;
import com.tekcapzule.course.domain.model.LMSCourse;
import java.util.List;


public interface CourseService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<LMSCourse> findAll();

    List<LMSCourse> findAllByTopicCode(String code);
    List<LMSCourse> findAllByDuration(String code, String duration);
    List<LMSCourse> findAllByLevel(String code, String courseLevel);
    List<LMSCourse> findByCourseId(List<String> courseIds);
    void recommend(RecommendCommand recommendCommand);
    void approve(ApproveCommand approveCommand);
}
