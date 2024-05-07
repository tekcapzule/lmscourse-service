package com.tekcapzule.course.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapzule.course.domain.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<Course> findAll() {

        return dynamo.scan(Course.class,new DynamoDBScanExpression());
    }

    @Override
    public List<Course> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Course.class, queryExpression);

    }

    @Override
    public List<Course> findAllByDuration(topicCode, duration) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));
        expAttributes.put(":duration", new AttributeValue().withS(duration));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#duration", "duration");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
                .withIndexName("durationGSI").withConsistentRead(false)
                .withKeyConditionExpression("#duration = :duration and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Course.class, queryExpression);
    }
    @Override
    public List<Course> findAllByLevel(topicCode, level) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":level", new AttributeValue().withS(level));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#level", "level");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
                .withIndexName("levelGSI").withConsistentRead(false)
                .withKeyConditionExpression("#level = :level and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Course.class, queryExpression);

    }

    @Override
    public Course findBy(String code) {
        return dynamo.load(Course.class, code);
    }

    @Override
    public Course save(Course course) {
        dynamo.save(course);
        return course;
    }
}
