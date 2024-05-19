package com.tekcapzule.course.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapzule.course.domain.model.LMSCourse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.tekcapzule.course.domain.repository.CourseDynamoRepository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class CourseRepositoryImpl implements CourseDynamoRepository {

    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public CourseRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<LMSCourse> findAll() {

        return dynamo.scan(LMSCourse.class,new DynamoDBScanExpression());
    }

    @Override
    public List<LMSCourse> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<LMSCourse> queryExpression = new DynamoDBQueryExpression<LMSCourse>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(LMSCourse.class, queryExpression);

    }

    @Override
    public List<LMSCourse> findAllByDuration(String topicCode, String duration) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));
        expAttributes.put(":duration", new AttributeValue().withS(duration));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#duration", "duration");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<LMSCourse> queryExpression = new DynamoDBQueryExpression<LMSCourse>()
                .withIndexName("durationGSI").withConsistentRead(false)
                .withKeyConditionExpression("#duration = :duration and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(LMSCourse.class, queryExpression);
    }
    @Override
    public List<LMSCourse> findAllByLevel(String topicCode, String level) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":level", new AttributeValue().withS(level));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#level", "level");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<LMSCourse> queryExpression = new DynamoDBQueryExpression<LMSCourse>()
                .withIndexName("levelGSI").withConsistentRead(false)
                .withKeyConditionExpression("#level = :level and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(LMSCourse.class, queryExpression);

    }

    @Override
    public LMSCourse findBy(String code) {
        return dynamo.load(LMSCourse.class, code);
    }

    @Override
    public LMSCourse save(LMSCourse course) {
        dynamo.save(course);
        return course;
    }
}
