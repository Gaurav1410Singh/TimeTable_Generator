package com.recb.timetable_generator.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.models.Room;
import com.recb.timetable_generator.models.Teacher;
import com.recb.timetable_generator.models.TimeTable;
import com.recb.timetable_generator.octaplanner.CustomSolver;
import com.recb.timetable_generator.repository.LessonRepository;
import com.recb.timetable_generator.repository.RoomRepository;
import com.recb.timetable_generator.repository.TeacherRepository;
import com.recb.timetable_generator.service.TimeTableService;
import com.recb.timetable_generator.utils.Timeslot;

@Service
public class TimeTableServiceImpl implements TimeTableService {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public String generate() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Timeslot> timeslots = Timeslot.getTimeslots();
        List<Teacher> secondaryTeachers = teacherRepository.findSecondaryTeachers();
        // List<Lesson> lessons = new ArrayList<Lesson>();
        // for (Lesson lesson : temp) {
        //     for(int i = 0; i < lesson.getLecturesPerWeek(); i++){
        //         lessons.add(lesson);
        //     }
        // }

        TimeTable data = new TimeTable(timeslots, rooms, lessons, secondaryTeachers);
        data = CustomSolver.solve(data);
        lessonRepository.saveAll(data.getLessonList());
        return "Timetable is generated you can check it out.";
    }

    @Override
    public List<Lesson> getTimeTableForClass(String group) {
        return lessonRepository.findByStudentGroup(group);
    }

    @Override
    public List<Lesson> getTimeTableForTracher(Long teacherId) {
        List<Lesson> lessons = lessonRepository.findByPrimaryTeacher_Id(teacherId);
        if(lessons.size() != 0)
        return lessons;
        else
        return lessonRepository.findBySecondaryTeacher_Id(teacherId);
    }

    @Override
    public List<Lesson> getTimeTableForRoom(Long roomNo) {
        return lessonRepository.findByRoom_RoomNo(roomNo);
    }

    // @Override
    // public List<Lesson> getTimeTableForTracher(String teacher) {
    //     return lessonRepository.findByPrimaryTeacher_Name(teacher);
    // }
    
}
