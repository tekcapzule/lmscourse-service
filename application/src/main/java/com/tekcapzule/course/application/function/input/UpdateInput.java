package com.tekcapzule.course.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.course.domain.model.*;
import com.tekcapzule.course.domain.model.Module;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UpdateInput {
    private String courseId;
    private String title;
    private String topicCode;
    private String author;
    private String publisher;
    private String duration;
    private String resourceUrl;
    private String publishedOn;
    private String summary;
    private String description;
    private List<Module> modules;
    private PrizingModel prizingModel;
    private DeliveryMode deliveryMode;
    private LearningMode learningMode;
    private String coverImageUrl;
    private int recommendations;
    private int points;
    private int courseRating;
    private Promotion promotion;
    private boolean earnBadge;
    private boolean earnCertification;
    private CourseType courseType;
    private CourseLevel courseLevel;
}
