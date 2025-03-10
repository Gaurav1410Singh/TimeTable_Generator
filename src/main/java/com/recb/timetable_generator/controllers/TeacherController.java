package com.recb.timetable_generator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recb.timetable_generator.models.Teacher;
import com.recb.timetable_generator.service.TeacherService;


@RestController
@RequestMapping("/timetable/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Get all teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    // Add a new teacher
    @PostMapping
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
        String message = teacherService.addTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Delete a teacher by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        String message = teacherService.deleteTeacher(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    // Update a teacher
    @PutMapping
    public ResponseEntity<String> updateTeacher(@RequestBody Teacher teacher) {
        String message = teacherService.updateTeacher(teacher);
        return ResponseEntity.ok(message);
    }

    // Get all secondary teachers
    @GetMapping("/secondary")
    public ResponseEntity<List<Teacher>> getSecondTeachers() {
        List<Teacher> secondaryTeachers = teacherService.getSecondTeachers();
        return ResponseEntity.ok(secondaryTeachers);
    }

    //Get teacher by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            return ResponseEntity.ok(teacher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}
