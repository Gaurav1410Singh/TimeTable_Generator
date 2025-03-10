package com.recb.timetable_generator.models;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recb.timetable_generator.utils.Timeslot;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PlanningEntity
@ToString
@Entity         // JPA annotation for database entity
@Table(name = "lessons") // Table name in the database
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate IDs
    private Long id;                       // Unique ID for the lesson
    private String subject;               // Subject name
    private boolean isPractical;
    private int lecturesPerWeek;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("lessons")
    @JoinColumn(name="primary_teacher_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher primaryTeacher;       // Fixed primary teacher
    
    private String studentGroup;          // Associated student group (e.g., "CSE-2024")

    @Embedded
    @PlanningVariable(valueRangeProviderRefs = "timeslotRange") // OptaPlanner variable
    private Timeslot timeslot;            // Time slot for the lesson
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("lessons")
    @JoinColumn(name = "room_no", referencedColumnName = "roomNo")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;                    // Assigned room for the lesson

    @JoinColumn(name="secondary_teacher_id", nullable=true, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @PlanningVariable(valueRangeProviderRefs = "secondaryTeacherRange")
    @JsonIgnoreProperties({"lessons", "secondaryLessons"})
    private Teacher secondaryTeacher;      // Dynamic secondary teacher (optional)

    // Custom constructor for initializing lessons without secondary teacher
    public Lesson(String subject, boolean isPractical, int lecturesPerWeek, Teacher primaryTeacher, String studentGroup) {
        // this.id = id;
        this.subject = subject;
        this.isPractical = isPractical;
        this.lecturesPerWeek = lecturesPerWeek;
        this.primaryTeacher = primaryTeacher;
        this.studentGroup = studentGroup;
        this.timeslot = null;
        this.room = null;
        this.secondaryTeacher = null; // Secondary teacher is dynamic
    }

    public Lesson(Long id,String subject, boolean isPractical, int lecturesPerWeek, Teacher primaryTeacher, String studentGroup) {
        this.id = id;
        this.subject = subject;
        this.isPractical = isPractical;
        this.lecturesPerWeek = lecturesPerWeek;
        this.primaryTeacher = primaryTeacher;
        this.studentGroup = studentGroup;
        this.timeslot = null;
        this.room = null;
        this.secondaryTeacher = null; // Secondary teacher is dynamic
    }

    @Override
    public int hashCode(){
        return Objects.hash(primaryTeacher,isPractical, studentGroup, secondaryTeacher, subject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lesson lesson = (Lesson) obj;
        return isPractical == lesson.isPractical &&
            Objects.equals(primaryTeacher, lesson.primaryTeacher) &&
            Objects.equals(studentGroup, lesson.studentGroup) &&
            Objects.equals(secondaryTeacher, lesson.secondaryTeacher) &&
            Objects.equals(subject, lesson.subject);
    }

}
