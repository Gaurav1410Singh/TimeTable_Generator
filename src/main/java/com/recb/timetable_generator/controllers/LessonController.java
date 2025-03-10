package com.recb.timetable_generator.controllers;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timetable/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public Optional<Lesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }

    @PutMapping
    public String updateLesson(@RequestBody Lesson lesson) {
        return lessonService.updateLesson(lesson);
    }

    @PostMapping
    public String addLesson(@RequestBody Lesson lesson) {
        return lessonService.addLessons(lesson);
    }

}
