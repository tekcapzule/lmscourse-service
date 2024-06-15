package com.tekcapzule.course.domain.service;

import com.tekcapzule.course.domain.command.ApproveCommand;
import com.tekcapzule.course.domain.command.CreateCommand;
import com.tekcapzule.course.domain.command.RecommendCommand;
import com.tekcapzule.course.domain.command.UpdateCommand;
import com.tekcapzule.course.domain.model.LMSCourse;
import com.tekcapzule.course.domain.model.Status;
import com.tekcapzule.course.domain.repository.CourseDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    private CourseDynamoRepository courseDynamoRepository;

    @Autowired
    public CourseServiceImpl(CourseDynamoRepository courseDynamoRepository) {
        this.courseDynamoRepository = courseDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create course service - Module Code :%s", createCommand.getTopicCode()));

        LMSCourse course = LMSCourse.builder()
                .title(createCommand.getTitle())
                .topicCode(createCommand.getTopicCode())
                .author(createCommand.getAuthor())
                .publisher(createCommand.getPublisher())
                .duration(createCommand.getDuration())
                .resourceUrl(createCommand.getResourceUrl())
                .summary(createCommand.getSummary())
                .description(createCommand.getDescription())
                .modules(createCommand.getModules())
                .prizingModel(createCommand.getPrizingModel())
                .deliveryMode(createCommand.getDeliveryMode())
                .learningMode(createCommand.getLearningMode())
                .coverImageUrl(createCommand.getCoverImageUrl())
                .points(createCommand.getPoints())
                .promotion(createCommand.getPromotion())
                .earnBadge(createCommand.isEarnBadge())
                .earnCertification(createCommand.isEarnCertification())
                .courseType(createCommand.getCourseType())
                .courseLevel(createCommand.getCourseLevel())
                .status(Status.SUBMITTED)
                .recommendations(createCommand.getRecommendations())
                .publishedOn(createCommand.getPublishedOn())
                .build();

        course.setAddedOn(createCommand.getExecOn());
        course.setAddedBy(createCommand.getExecBy().getUserId());

        courseDynamoRepository.save(course);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update course service - Course ID:%s", updateCommand.getCourseId()));

        LMSCourse course = courseDynamoRepository.findBy(updateCommand.getCourseId());
        if (course != null) {
            course.setTitle(updateCommand.getTitle());
            course.setTopicCode(updateCommand.getTopicCode());
            course.setAuthor(updateCommand.getAuthor());
            course.setPublisher(updateCommand.getPublisher());
            course.setDuration(updateCommand.getDuration());
            course.setResourceUrl(updateCommand.getResourceUrl());
            course.setSummary(updateCommand.getSummary());
            course.setDescription(updateCommand.getDescription());
            course.setModules(updateCommand.getModules());
            course.setPrizingModel(updateCommand.getPrizingModel());
            course.setDeliveryMode(updateCommand.getDeliveryMode());
            course.setLearningMode(updateCommand.getLearningMode());
            course.setEarnBadge(updateCommand.isEarnBadge());
            course.setEarnCertification(updateCommand.isEarnCertification());
            course.setPromotion(updateCommand.getPromotion());
            course.setCoverImageUrl(updateCommand.getCoverImageUrl());
            course.setUpdatedOn(updateCommand.getExecOn());
            course.setUpdatedBy(updateCommand.getExecBy().getUserId());
            course.setRecommendations(updateCommand.getRecommendations());
            course.setPublishedOn(updateCommand.getPublishedOn());
            course.setPoints(updateCommand.getPoints());
            course.setCourseRating(updateCommand.getCourseRating());
            course.setCourseType(updateCommand.getCourseType());
            course.setCourseLevel(updateCommand.getCourseLevel());
            courseDynamoRepository.save(course);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend course service -  Course Id:%s", recommendCommand.getCourseId()));

        try {
            LMSCourse course = courseDynamoRepository.findBy(recommendCommand.getCourseId());
            if (course != null) {
                Integer recommendationsCount = course.getRecommendations();
                recommendationsCount += 1;
                course.setRecommendations(recommendationsCount);

                course.setUpdatedOn(recommendCommand.getExecOn());
                course.setUpdatedBy(recommendCommand.getExecBy().getUserId());

                courseDynamoRepository.save(course);
            }

        } catch(Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve course service -  course Id:%s", approveCommand.getCourseId()));

        LMSCourse course = courseDynamoRepository.findBy(approveCommand.getCourseId());
        if (course != null) {
            course.setStatus(Status.ACTIVE);

            course.setUpdatedOn(approveCommand.getExecOn());
            course.setUpdatedBy(approveCommand.getExecBy().getUserId());

            courseDynamoRepository.save(course);
        }
    }

   /* @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Module Code:%s", disableCommand.getCode()));

        courseDynamoRepository.findBy(disableCommand.getCode());
        Module topic = courseDynamoRepository.findBy(disableCommand.getCode());
        if (topic != null) {
            topic.setStatus("INACTIVE");
            topic.setUpdatedOn(disableCommand.getExecOn());
            topic.setUpdatedBy(disableCommand.getExecBy().getUserId());
            courseDynamoRepository.save(topic);
        }
    }*/

    @Override
    public List<LMSCourse> findAll() {

        log.info("Entering findAll Course service");

        return courseDynamoRepository.findAll();
    }

    @Override
    public List<LMSCourse> findAllByTopicCode(String topicCode) {

        log.info(String.format("Entering findAllByTopicCode Course service - Module code:%s", topicCode));

        return courseDynamoRepository.findAllByTopicCode(topicCode);
    }
    @Override
    public List<LMSCourse> findAllByDuration(String topicCode, String duration) {

        log.info(String.format("Entering findAllByDuration Course service - Module code:%s", topicCode));

        return courseDynamoRepository.findAllByDuration(topicCode, duration);
    }
    @Override
    public List<LMSCourse> findAllByLevel(String topicCode, String courseLevel) {

        log.info(String.format("Entering findAllByLevel Course service - Module code:%s", topicCode));

        return courseDynamoRepository.findAllByLevel(topicCode, courseLevel);
    }

    @Override
    public List<LMSCourse> findByCourseId(List<String> courseIds) {
        log.info(String.format("Entering findByCourseId course service - Course ID:%s", courseIds.get(0)));
        List<LMSCourse> lmsCourses = new ArrayList();
        for(String courseId:courseIds) {
           lmsCourses.add(courseDynamoRepository.findBy(courseId));
        }
        return lmsCourses;
    }


}
