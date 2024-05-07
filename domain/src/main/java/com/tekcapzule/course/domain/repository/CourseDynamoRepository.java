package com.tekcapzule.course.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.course.domain.model.Course;

import java.util.List;

public interface CourseDynamoRepository extends CrudRepository<Course, String> {

    List<Course> findAllByTopicCode(String topicCode);
    List<Course> findAllByDuration(topicCode, duration);
    List<Course> findAllByLevel(topicCode, level);

}
