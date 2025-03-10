package com.recb.timetable_generator.service;

import java.util.List;
import java.util.Optional;

import com.recb.timetable_generator.models.Lesson;

public interface LessonService {
    List<Lesson> getAllLessons();
    Optional<Lesson> getLessonById(Long id);
    String deleteLesson(Long id);
    String updateLesson(Lesson lesson);
    String addLessons(Lesson lesson);
    // List<Lesson> getLessonsByStudentGroup(String studentGroup);
}
