package com.recb.timetable_generator.octaplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.utils.Timeslot;

import java.util.function.Function;
import java.time.LocalTime;

public class TimeTableConstraintProvider implements ConstraintProvider {



        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
                return new Constraint[] {
                                // Hard Constraints
                                teacherConflict(constraintFactory),
                                classroomConflict(constraintFactory),
                                studentGroupConflict(constraintFactory),
                                secondaryTeacherValidAssignment(constraintFactory),
                                secondaryTeacherConflict(constraintFactory),
                                roomTypeMatch(constraintFactory),

                                // Soft Constraints
                                needFourthLectureFree(constraintFactory),
                                allLecturesBefore1PM(constraintFactory),
                                allLabsAfter1PM(constraintFactory),
                                evenlyDistributedLectures(constraintFactory),
                };
        }

        // ---------------------
        // Hard Constraints
        // ---------------------

        // i) Teacher Conflict
        private Constraint teacherConflict(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .join(Lesson.class,
                                                Joiners.equal(Lesson::getPrimaryTeacher), // Same teacher
                                                Joiners.equal(Lesson::getTimeslot),
                                                Joiners.lessThan(Lesson::getId)) // Same time slot
                                .penalize(HardSoftScore.ONE_HARD).asConstraint("Teacher conflict");
        }

        // ii) Classroom Conflict
        private Constraint classroomConflict(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .join(Lesson.class,
                                                Joiners.equal(Lesson::getRoom), // Same classroom
                                                Joiners.equal(Lesson::getTimeslot),
                                                Joiners.lessThan(Lesson::getId)) // Same time slot
                                .penalize(HardSoftScore.ONE_HARD).asConstraint("Classroom conflict");
        }

        // // room type check
        private Constraint roomTypeMatch(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .filter(lesson -> lesson.getRoom() != null) // Ensure a room is assigned
                                .filter(lesson -> {
                                        // Check if the room type matches the lesson type
                                        String requiredRoomType = lesson.isPractical() ? "Lab" : "Lecture Hall";
                                        return !lesson.getRoom().getType().equals(requiredRoomType);
                                })
                                .penalize(HardSoftScore.ONE_HARD)
                                .asConstraint("Room type mismatch");
        }

        // iii) Student Group Conflict
        private Constraint studentGroupConflict(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .join(Lesson.class,
                                                Joiners.equal(Lesson::getStudentGroup), // Same student group
                                                Joiners.equal(Lesson::getTimeslot),
                                                Joiners.lessThan(Lesson::getId)) // Same time slot
                                .penalize(HardSoftScore.ONE_HARD).asConstraint("Student group conflict");
        }

        // iv) Secondary Teacher Valid Assignment
        private Constraint secondaryTeacherValidAssignment(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                // Apply the constraint only to practical lessons
                                .filter(Lesson::isPractical)
                                // Skip lessons where a secondary teacher is not assigned
                                .filter(lesson -> lesson.getSecondaryTeacher() != null)
                                // Ensure that the assigned secondary teacher is in the provided list of valid
                                // secondary teachers
                                .filter(lesson -> lesson.getSecondaryTeacher() != null
                                                && !lesson.getSecondaryTeacher().getDepartment()
                                                                .equals(lesson.getPrimaryTeacher().getDepartment())) // Ensure
                                                                                                                     // department
                                                                                                                     // match
                                .penalize(HardSoftScore.ONE_HARD) // Apply a penalty for invalid assignment
                                .asConstraint("Invalid secondary teacher department or assignment for practical lesson");
        }

        // v) Secondary Teacher Conflict
        private Constraint secondaryTeacherConflict(ConstraintFactory factory) {
                return factory.forEach(Lesson.class).filter(lesson -> lesson.getSecondaryTeacher() != null)
                                .join(Lesson.class, Joiners.equal(Lesson::getSecondaryTeacher), // Same secondary
                                                                                                // teacher
                                                Joiners.equal(Lesson::getTimeslot)) // Same time slot
                                .penalize(HardSoftScore.ONE_HARD).asConstraint("Secondary teacher conflict");
        }

        // ---------------------
        // Soft Constraints
        // ---------------------

        // i) Need Fourth Lecture Free
        private Constraint needFourthLectureFree(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .filter(lesson -> lesson.getPrimaryTeacher().isNeedsFreeFourthLecture() && // Check if
                                                                                                           // the
                                                                                                           // teacher
                                                                                                           // needs a
                                                                                                           // free
                                                                                                           // fourth
                                                                                                           // lecture
                                                isFourthLecture(lesson.getTimeslot())) // Check if the timeslot is the
                                                                                       // fourth lecture
                                .penalize(HardSoftScore.ONE_HARD)
                                .asConstraint("Teacher preference not met (fourth lecture free)");
        }

        // Helper method to determine if a timeslot is the fourth lecture
        private boolean isFourthLecture(Timeslot timeslot) {
                return timeslot.getStartTime().equals(LocalTime.of(12, 0)) && // Starts at 11:00 AM
                                timeslot.getEndTime().equals(LocalTime.of(13, 0)); // Ends at 12:00 PM
        }

        // ii) All Lectures Should Be Before 1PM
        private Constraint allLecturesBefore1PM(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .filter(lesson -> lesson.getTimeslot().getStartTime().isAfter(LocalTime.of(13, 0))) // After
                                                                                                                    // 1
                                                                                                                    // PM
                                .penalize(HardSoftScore.ONE_SOFT).asConstraint("Lecture scheduled after 1 PM");
        }

        // iii) All Labs (2-Hour Lectures) Should Be After 1PM
        private Constraint allLabsAfter1PM(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .filter(lesson -> lesson.isPractical() && // Is a lab
                                                lesson.getTimeslot().getStartTime().isBefore(LocalTime.of(13, 0))) // Before
                                                                                                                   // 1
                                                                                                                   // PM
                                .penalize(HardSoftScore.ONE_SOFT).asConstraint("Lab scheduled before 1 PM");
        }

        private Constraint evenlyDistributedLectures(ConstraintFactory factory) {
                return factory.forEach(Lesson.class)
                                .groupBy(Function.identity(), // Group by Lesson object itself
                                                ConstraintCollectors.count()) // Count occurrences
                                .penalize(HardSoftScore.ONE_SOFT,
                                                (lesson, count) -> Math.abs(count - lesson.getLecturesPerWeek()))
                                .asConstraint("Unevenly distributed lectures");
        }

}




