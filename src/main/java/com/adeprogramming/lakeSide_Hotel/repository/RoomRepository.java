package com.adeprogramming.lakeSide_Hotel.repository;

import com.adeprogramming.lakeSide_Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT DISTINCT(r.roomType) FROM Room r")
    List<String> findDistinctRoomTypes();
}
