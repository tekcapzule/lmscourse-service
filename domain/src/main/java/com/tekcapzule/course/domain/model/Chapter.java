package com.tekcapzule.course.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Chapter {
    private int serialNumber;
    private String duration;
    private String name;
    private String coverImageUrl;
    private String startDate;
    private String endDate;
    private String resourceUrl;
    @DynamoDBAttribute(attributeName = "chapterType")
    @DynamoDBTypeConvertedEnum
    private ChapterType chapterType;

}
