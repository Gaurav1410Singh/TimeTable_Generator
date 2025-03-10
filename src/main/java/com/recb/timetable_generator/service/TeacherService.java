package com.recb.timetable_generator.service;

import java.util.List;

import com.recb.timetable_generator.models.Teacher;

public interface TeacherService {
    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Long id);
    String addTeacher(Teacher teacher);
    String deleteTeacher(Long id);
    String updateTeacher(Teacher teacher);
    List<Teacher> getSecondTeachers();
}
