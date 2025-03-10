package com.recb.timetable_generator.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recb.timetable_generator.models.Lesson;
import com.recb.timetable_generator.models.Room;
import com.recb.timetable_generator.repository.LessonRepository;
import com.recb.timetable_generator.repository.RoomRepository;
import com.recb.timetable_generator.service.RoomService;

import jakarta.transaction.Transactional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public List<Room> getAllRooms() {
        // Fetch all rooms from the repository
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(Long id) {
        // Fetch a specific room by its ID
        Optional<Room> room = roomRepository.findById(id);
        return room.orElseThrow(() -> new RuntimeException("Room with ID " + id + " not found."));
    }

    @Override
    @Transactional
    public String deleteRoom(Long id) {
        // Check if the room exists before attempting to delete
        if (roomRepository.existsById(id)) {
            List<Lesson> lessons = lessonRepository.findByRoom_RoomNo(id);

            // Delete all associated lessons
            lessonRepository.deleteAll(lessons);

            roomRepository.deleteById(id);
            return "Room with room no. " + id + " has been deleted successfully.";
        } else {
            return "Room with room no. " + id + " does not exist.";
        }
    }

    @Override
    @Transactional
    public String updateRoom(Room room) {
        // Check if the room exists before updating
        if (room.getRoomNo() == null || !roomRepository.existsById(room.getRoomNo())) {
            return "Room with room no. " + room.getRoomNo() + " does not exist. Update failed.";
        }
        roomRepository.save(room);
        List<Lesson> lessons = lessonRepository.findByRoom_RoomNo(room.getRoomNo());
        for (Lesson lesson : lessons) {
            lesson.setRoom(room); // This may not be necessary if the reference is already correct
        }
        lessonRepository.saveAll(lessons);
        return "Room with room no. " + room.getRoomNo() + " has been updated successfully.";
    }

    @Override
    public String addRoom(Room room) {
        // Save a new room to the database
        roomRepository.save(room);
        return "Room has been added successfully.";
    }
}
