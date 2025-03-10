package com.recb.timetable_generator.models;

import lombok.*;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import com.recb.timetable_generator.utils.Timeslot;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PlanningSolution
public class TimeTable {

    @ValueRangeProvider(id = "timeslotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;

    @ValueRangeProvider(id = "roomRange")
    @ProblemFactCollectionProperty
    private List<Room> roomList;

    @ValueRangeProvider(id = "secondaryTeacherRange")
    @ProblemFactCollectionProperty
    private List<Teacher> secondaryTeacherList;

    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;

    @PlanningScore
    @Builder.Default
    private HardSoftScore score = HardSoftScore.ZERO; // Initialize score to zero

    // Explicit constructor to ensure it is defined
    public TimeTable(List<Timeslot> timeslotList, List<Room> roomList, List<Lesson> lessonList, List<Teacher> secondTeacherList) {
        this.timeslotList = timeslotList;
        this.roomList = roomList;
        this.lessonList = lessonList;
        this.secondaryTeacherList = secondTeacherList;
        this.score = HardSoftScore.ZERO; // Initialize score to zero
    }

}