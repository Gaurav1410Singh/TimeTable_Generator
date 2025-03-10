package com.recb.timetable_generator.utils;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Timeslot {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static List<Timeslot> getTimeslots(){
        return List.of(
            // Monday Timeslots
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.MONDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)),
            // tuesday
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)),
            //wenesday
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)),
    
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)),
    
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)),
    
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(12, 0), LocalTime.of(1, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(2, 0), LocalTime.of(3, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)),
            new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(4, 0), LocalTime.of(5, 0))
        );
        
    }
}