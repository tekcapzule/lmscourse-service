package com.tekcapzule.course.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ChapterType {
    VIDEO_CONTENT,
    AUDIO_CONTENT,
    PDF_CONTENT,
    QUIZ,
    ASSIGNMENT,
    LAB;
}