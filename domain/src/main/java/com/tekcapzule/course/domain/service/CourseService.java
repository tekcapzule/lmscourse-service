package com.tekcapzule.course.domain.service;

import com.tekcapzule.course.domain.command.ApproveCommand;
import com.tekcapzule.course.domain.command.CreateCommand;
import com.tekcapzule.course.domain.command.RecommendCommand;
import com.tekcapzule.course.domain.command.UpdateCommand;
import com.tekcapzule.course.domain.model.Course;
import java.util.List;


public interface CourseService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<Course> findAll();

    List<Course> findAllByTopicCode(String code);
    List<Course> findAllByDuration(String code, String duration);
    List<Course> findAllByLevel(String code, String level);
    void recommend(RecommendCommand recommendCommand);
    void approve(ApproveCommand approveCommand);
}
