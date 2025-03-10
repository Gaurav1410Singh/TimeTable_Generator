package com.recb.timetable_generator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recb.timetable_generator.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
    @Query("SELECT t FROM Teacher t WHERE t.isSecondary = true")
    List<Teacher> findSecondaryTeachers();
} 
