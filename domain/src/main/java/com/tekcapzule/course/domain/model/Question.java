package com.tekcapzule.course.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Question {
    private String questionId;
    private String questionText;
    @DynamoDBAttribute(attributeName = "options")
    private List<Option> options;
    @Getter(onMethod_ = {@DynamoDBIgnore})
    private List<String> correctAnswer;

}
