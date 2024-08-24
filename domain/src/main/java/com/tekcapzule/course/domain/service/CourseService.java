package com.tekcapzule.course.domain.service;

import com.tekcapzule.course.domain.command.*;
import com.tekcapzule.course.domain.model.LMSCourse;
import com.tekcapzule.course.domain.model.QuizResult;

import java.util.List;


public interface CourseService {

    void create(CreateCommand createCommand);
    void createQuiz(CreateQuizCommand createQuizCommand);
    void update(UpdateCommand updateCommand);
    List<LMSCourse> findAll();
    List<LMSCourse> findAllByTopicCode(String code);
    List<LMSCourse> findAllByDuration(String code, String duration);
    List<LMSCourse> findAllByLevel(String code, String courseLevel);
    List<LMSCourse> findByCourseId(List<String> courseIds);
    void recommend(RecommendCommand recommendCommand);
    void approve(ApproveCommand approveCommand);
    QuizResult submit(QuizSubmitCommand quizSubmitCommand);
}
