package com.recb.timetable_generator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recb.timetable_generator.models.Room;
import com.recb.timetable_generator.service.RoomService;

@RestController
@RequestMapping("/timetable/rooms")

public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    // Get a specific room by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoom(id);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Add a new room
    @PostMapping
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        String message = roomService.addRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Update an existing room
    @PutMapping
    public ResponseEntity<String> updateRoom(@RequestBody Room room) {
        String message = roomService.updateRoom(room);
        return ResponseEntity.ok(message);
    }

    // Delete a room by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        String message = roomService.deleteRoom(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
