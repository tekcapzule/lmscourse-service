package com.tekcapzule.course.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
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
    private int duration;
    private String name;
    private String coverImageUrl;
    private String startDate;
    private String endDate;
    private ChapterType chapterType;

}
