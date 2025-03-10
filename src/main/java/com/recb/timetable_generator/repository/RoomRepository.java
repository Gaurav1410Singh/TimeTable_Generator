package com.recb.timetable_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recb.timetable_generator.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
    
}
