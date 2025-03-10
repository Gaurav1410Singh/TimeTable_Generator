package com.recb.timetable_generator.service;

import java.util.List;

import com.recb.timetable_generator.models.Room;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoom(Long id);
    String deleteRoom(Long id);
    String updateRoom(Room room);
    String addRoom(Room room);
} 
