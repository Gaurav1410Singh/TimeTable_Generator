package com.recb.timetable_generator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.service.implementation.TimeTableServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {
    
    @Autowired
    TimeTableServiceImpl timeTableService;

    @GetMapping("/group/{studentGroup}")
    public List<Lesson> getTimeTableForClass(@PathVariable String studentGroup) {
        return timeTableService.getTimeTableForClass(studentGroup);
    }
    
    @GetMapping("/faculty/{teacherId}")
    public List<Lesson> getTimeTableForTeacher(@PathVariable Long teacherId) {
        return timeTableService.getTimeTableForTracher(teacherId);
    }

    @GetMapping("/generate")
    public String generateTimeTable() {
        return timeTableService.generate();
    }
    
    @GetMapping("/room/{roomNo}")
    public List<Lesson> getMethodName(@PathVariable Long roomNo) {
        return timeTableService.getTimeTableForRoom(roomNo);
    }
    
}
