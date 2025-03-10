package com.recb.timetable_generator.service;

import java.util.List;

import com.recb.timetable_generator.models.Lesson;

public interface TimeTableService {
    String generate();
    List<Lesson> getTimeTableForClass(String group);
    List<Lesson> getTimeTableForTracher(Long teacherId);
    List<Lesson> getTimeTableForRoom(Long roomNo);
}
