package com.tekcapzule.course.application.function.input;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.course.domain.model.*;
import com.tekcapzule.course.domain.model.Module;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String title;
    private String topicCode;
    private String author;
    private String publisher;
    private String duration;
    private String resourceUrl;
    private String summary;
    private String description;
    private List<Module> modules;
    private PrizingModel prizingModel;
    private DeliveryMode deliveryMode;
    private LearningMode learningMode;
    private String coverImageUrl;
    private boolean earnBadge;
    private boolean earnCertification;
    private int points;
    private CourseType courseType;
    private CourseLevel courseLevel;
    private int recommendations;
    private String publishedOn;
    private Promotion promotion;
    private List<Resource> resources;
    private List<Announcement> announcements;
    private Quiz assessment;
    private List<String> learningObjectives;
    private List<String> preRequisites;
    private List<String> targetedAudiences;
    private List<String> faqs;
}