package com.tekcapzule.course.domain.service;

import com.tekcapzule.course.domain.command.*;
import com.tekcapzule.course.domain.model.*;
import com.tekcapzule.course.domain.repository.CourseDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .assessment(createCommand.getAssessment())
                .resources(createCommand.getResources())
                .announcements(createCommand.getAnnouncements())
                .learningObjectives(createCommand.getLearningObjectives())
                .preRequisites(createCommand.getPreRequisites())
                .targetedAudiences(createCommand.getTargetedAudiences())
                .faqs(createCommand.getFaqs())
                .build();

        course.setAddedOn(createCommand.getExecOn());
        course.setAddedBy(createCommand.getExecBy().getUserId());

        courseDynamoRepository.save(course);
    }

    private Quiz buildQuiz(Quiz assessment) {
        String quizId = UUID.randomUUID().toString();
        Quiz quiz = Quiz.builder().quizId(quizId).questions(assessment.getQuestions()).build();
        return quiz;
    }

    @Override
    public void createQuiz(CreateQuizCommand createQuizCommand) {
        log.info(String.format("Entering create quiz service - Course ID :%s", createQuizCommand.getCourseId()));
        LMSCourse course = courseDynamoRepository.findBy(createQuizCommand.getCourseId());
        String quizId = UUID.randomUUID().toString();
        Quiz quiz = Quiz.builder().quizId(quizId).questions(createQuizCommand.getQuestions()).build();
        course.setAssessment(quiz);

        course.setAddedOn(createQuizCommand.getExecOn());
        course.setAddedBy(createQuizCommand.getExecBy().getUserId());

        courseDynamoRepository.save(course);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update course service - Course ID:%s", updateCommand.getCourseId()));

        LMSCourse course = courseDynamoRepository.findBy(updateCommand.getCourseId());
        if (course != null) {
            course.setTitle(updateCommand.getTitle()!=null?updateCommand.getTitle():course.getTitle());
            course.setTopicCode(updateCommand.getTopicCode()!=null?updateCommand.getTopicCode():course.getTopicCode());
            course.setAuthor(updateCommand.getAuthor()!=null?updateCommand.getAuthor():course.getAuthor());
            course.setPublisher(updateCommand.getPublisher()!=null?updateCommand.getPublisher():course.getPublisher());
            course.setDuration(updateCommand.getDuration()!=0?updateCommand.getDuration():course.getDuration());
            course.setResourceUrl(updateCommand.getResourceUrl()!=null?updateCommand.getResourceUrl():course.getResourceUrl());
            course.setSummary(updateCommand.getPublisher()!=null?updateCommand.getSummary():course.getSummary());
            course.setDescription(updateCommand.getDescription()!=null?updateCommand.getDescription():course.getDescription());
            course.setModules(updateCommand.getModules()!=null?updateCommand.getModules():course.getModules());
            course.setPrizingModel(updateCommand.getPrizingModel()!=null?updateCommand.getPrizingModel():course.getPrizingModel());
            course.setDeliveryMode(updateCommand.getDeliveryMode()!=null?updateCommand.getDeliveryMode():course.getDeliveryMode());
            course.setLearningMode(updateCommand.getLearningMode()!=null?updateCommand.getLearningMode():course.getLearningMode());
            course.setEarnBadge(updateCommand.isEarnBadge());
            course.setEarnCertification(updateCommand.isEarnCertification());
            course.setPromotion(updateCommand.getPromotion()!=null?updateCommand.getPromotion():course.getPromotion());
            course.setCoverImageUrl(updateCommand.getCoverImageUrl()!=null?updateCommand.getCoverImageUrl():course.getCoverImageUrl());
            course.setUpdatedOn(updateCommand.getExecOn());
            course.setUpdatedBy(updateCommand.getExecBy().getUserId());
            course.setRecommendations(updateCommand.getRecommendations());
            course.setPublishedOn(updateCommand.getPublishedOn()!=null?updateCommand.getPublishedOn():course.getPublishedOn());
            course.setPoints(updateCommand.getPoints()!=0? updateCommand.getPoints() : course.getPoints());
            course.setCourseRating(updateCommand.getCourseRating()!=0?updateCommand.getCourseRating(): course.getCourseRating());
            course.setCourseType(updateCommand.getCourseType()!=null?updateCommand.getCourseType():course.getCourseType());
            course.setCourseLevel(updateCommand.getCourseLevel()!=null?updateCommand.getCourseLevel():course.getCourseLevel());
            course.setAssessment(updateCommand.getAssessment()!=null?updateCommand.getAssessment():course.getAssessment());
            course.setResources(updateCommand.getResources()!=null?updateCommand.getResources():course.getResources());
            course.setAnnouncements(updateCommand.getAnnouncements()!=null?updateCommand.getAnnouncements():course.getAnnouncements());
            course.setLearningObjectives(updateCommand.getLearningObjectives()!=null?updateCommand.getLearningObjectives():course.getLearningObjectives());
            course.setPreRequisites(updateCommand.getPreRequisites()!=null?updateCommand.getPreRequisites():course.getPreRequisites());
            course.setTargetedAudiences(updateCommand.getTargetedAudiences()!=null?updateCommand.getTargetedAudiences():course.getTargetedAudiences());
            course.setFaqs(updateCommand.getFaqs()!=null?updateCommand.getFaqs():course.getFaqs());
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

    @Override
    public QuizResult submit(QuizSubmitCommand quizSubmitCommand) {
        log.info(String.format("Entering submit quiz service -  Course Id:%s", quizSubmitCommand.getCourseId()));
        QuizResult result = new QuizResult();
        int score = 0;
        boolean isPassed = false;
        double scorePercentage = 0;
        try {
            LMSCourse course = courseDynamoRepository.findBy(quizSubmitCommand.getCourseId());
            if (course != null) {
                Quiz quiz = course.getAssessment();
                if(quiz != null) {
                    List feedbackList = new ArrayList();
                    for(QuizSubmitCommand.UserAnswer userAnswer:quizSubmitCommand.getUserAnswers()) {
                        Question question = quiz.getQuestions().stream()
                                .filter(q -> q.getQuestionId().equals(userAnswer.getQuestionId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

                        boolean isCorrect = question.getCorrectAnswer().equals(userAnswer.getSelectedAnswers());
                        if (isCorrect) {
                            score++;
                        }

                        feedbackList.add(QuizResult.AnswersFeedback.builder().questionId(userAnswer.getQuestionId())
                                .correctAnswers(question.getCorrectAnswer()).selectedAnswers(userAnswer.getSelectedAnswers())
                                .build());


                    }
                    log.info("score value"+score);
                    log.info("size value"+quiz.getQuestions().size());
                    scorePercentage = ((double)score / quiz.getQuestions().size()) * 100;
                    log.info("score percentage"+scorePercentage);
                    isPassed = scorePercentage>50;
                    return new QuizResult(scorePercentage,isPassed, feedbackList);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
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
    public List<LMSCourse> findAllByDuration(String topicCode, int duration) {

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
