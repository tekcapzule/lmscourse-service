package com.tekcapsule.course.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Level {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Proficient"),
    MIXED("Mixed");

    @Getter
    private String value;
}