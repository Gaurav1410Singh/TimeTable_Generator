package com.recb.timetable_generator.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recb.timetable_generator.models.Teacher;
import com.recb.timetable_generator.repository.TeacherRepository;
import com.recb.timetable_generator.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        // Fetch all teachers from the repository
        return teacherRepository.findAll();
        
    }

    @Override
    public String addTeacher(Teacher teacher) {
        // Save the teacher to the database
        teacherRepository.save(teacher);
        return "Teacher has been added successfully.";
    }

    @Override
    public String deleteTeacher(Long id) {
        // Check if the teacher exists before attempting to delete
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return "Teacher with ID " + id + " has been deleted successfully.";
        } else {
            return "Teacher with ID " + id + " does not exist.";
        }
    }

    @Override
    public String updateTeacher(Teacher teacher) {
        // Check if the teacher exists before updating
        if (teacher.getId() == null) {
            return "Teacher with ID " + teacher.getId() + " does not exist. Update failed.";
        }
        teacherRepository.save(teacher);
        return "Teacher with ID " + teacher.getId() + " has been updated successfully.";
    }

    @Override
    public List<Teacher> getSecondTeachers() {
        // Fetch all teachers who can act as secondary teachers
        return teacherRepository.findSecondaryTeachers(); // Modify this if specific criteria are needed
    }

    @Override
    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElseThrow(() -> new RuntimeException("Teacher with ID " + id + " not found."));
    }
}
