package com.adeprogramming.lakeSide_Hotel.service;

import com.adeprogramming.lakeSide_Hotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    byte[] getRoomPhotosByRoomId(Long id) throws SQLException;

    List<Room> getAllRooms();

    void deleteRoom(Long id);

    Room updateRoom(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<Room> getRoomById(Long id);
}
