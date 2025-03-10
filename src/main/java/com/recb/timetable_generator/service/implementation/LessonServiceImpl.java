package com.recb.timetable_generator.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.repository.LessonRepository;
import com.recb.timetable_generator.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLessons() {
        // Fetch all lessons from the repository
        List<Lesson> allLessons = lessonRepository.findAll();  
        Set<Lesson> uniqueLessons = new HashSet<>();
        allLessons.forEach(lesson -> uniqueLessons.add(lesson));
        List<Lesson> lessons = new ArrayList<>();
        uniqueLessons.forEach(lesson -> lessons.add(lesson));
        return lessons;
    }

    @Override
    public Optional<Lesson> getLessonById(Long id) {
        // Fetch a lesson by its ID
        return lessonRepository.findById(id);
    }

    @Override
    public String deleteLesson(Long id) {
        // Check if the lesson exists before attempting to delete
        if (lessonRepository.existsById(id)) {
            Lesson lesson = lessonRepository.findById(id).orElse(null);
            List<Lesson> lessons = lessonRepository.findByPrimaryTeacherAndIsPracticalAndStudentGroupAndSecondaryTeacherAndSubject(lesson.getPrimaryTeacher() , lesson.isPractical(), lesson.getStudentGroup(), lesson.getSecondaryTeacher(), lesson.getSubject()); 
            lessonRepository.deleteAll(lessons);
            return "Lesson with ID " + id + " has been deleted successfully.";
        } else {
            return "Lesson with ID " + id + " does not exist.";
        }
    }

    @Override
public String updateLesson(Lesson lesson) {
    // Fetch all lessons matching the given lesson's properties
    Lesson temp = lessonRepository.findById(lesson.getId()).orElse(null);
    List<Lesson> lessons = lessonRepository.findByPrimaryTeacherAndIsPracticalAndStudentGroupAndSecondaryTeacherAndSubject(
        temp.getPrimaryTeacher(), temp.isPractical(), temp.getStudentGroup(), temp.getSecondaryTeacher(), temp.getSubject()
    ); 

    if (lessons.isEmpty()) {
        return "No matching lessons found for update.";
    }

    // Update the properties of each matching lesson
    for (Lesson l : lessons) {
        l.setPractical(lesson.isPractical());
        l.setPrimaryTeacher(lesson.getPrimaryTeacher());
        l.setStudentGroup(lesson.getStudentGroup());
        l.setSubject(lesson.getSubject());
    }

    // Save the updated lessons to the database
    lessonRepository.saveAll(lessons);

    return "Updated " + lessons.size() + " lesson(s) successfully.";
}


    @Override
    public String addLessons(Lesson lesson) {
        List<Lesson> lessons = new ArrayList<Lesson>();
    
        for(int i = 0; i < lesson.getLecturesPerWeek(); i++){
            Lesson newLesson = new Lesson(
            lesson.getSubject(),
            lesson.isPractical(),
            lesson.getLecturesPerWeek(),
            lesson.getPrimaryTeacher(),
            lesson.getStudentGroup()
        );
            lessons.add(newLesson);
        }

        lessonRepository.saveAll(lessons);
        return "Lesson has been added successfully.";
    }

    // @Override
    // public List<Lesson> getLessonsByStudentGroup(String studentGroup) {
    //     // Fetch lessons filtered by student group
    //     return lessonRepository.findByStudentGroup(studentGroup);
    // }

    
}
