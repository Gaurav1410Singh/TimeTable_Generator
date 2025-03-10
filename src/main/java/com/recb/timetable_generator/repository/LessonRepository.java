package com.recb.timetable_generator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.models.Teacher;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
    List<Lesson> findByStudentGroup(String studentGroup);
    List<Lesson> findByPrimaryTeacher_Name(String teacherName);
    List<Lesson> findByPrimaryTeacher_Id(Long id);
    List<Lesson> findBySecondaryTeacher_Id(Long id);
    List<Lesson> findByRoom_RoomNo(Long id);
    List<Lesson> findByPrimaryTeacherAndIsPracticalAndStudentGroupAndSecondaryTeacherAndSubject(
        Teacher primaryTeacher, boolean isPractical, String studentGroup, Teacher secondaryTeacher, String subject
    );
} 