package com.tekcapzule.course.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.course.domain.model.LMSCourse;

import java.util.List;

public interface CourseDynamoRepository extends CrudRepository<LMSCourse, String> {

    List<LMSCourse> findAllByTopicCode(String topicCode);
    List<LMSCourse> findAllByDuration(String topicCode, String duration);
    List<LMSCourse> findAllByLevel(String topicCode, String courseLevel);

}
