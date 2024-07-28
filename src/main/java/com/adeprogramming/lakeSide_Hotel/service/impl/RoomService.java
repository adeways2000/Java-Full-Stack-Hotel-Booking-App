package com.adeprogramming.lakeSide_Hotel.service.impl;

import com.adeprogramming.lakeSide_Hotel.exception.InternalServerException;
import com.adeprogramming.lakeSide_Hotel.exception.ResourceNotFoundException;
import com.adeprogramming.lakeSide_Hotel.model.Room;
import com.adeprogramming.lakeSide_Hotel.repository.RoomRepository;
import com.adeprogramming.lakeSide_Hotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public byte[] getRoomPhotosByRoomId(Long id) throws SQLException {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) throw new ResourceNotFoundException("Sorry room not found");
        Blob photoBlob = room.get().getPhoto();
        if (photoBlob != null) return photoBlob.getBytes(1, (int) photoBlob.length());
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void deleteRoom(Long id) {
        if (roomRepository.existsById(id)) roomRepository.deleteById(id);
    }

    @Override
    public Room updateRoom(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch(SQLException ex) {
                throw new InternalServerException("Error updating room");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long id) {

        return roomRepository.findById(id);
    }
}
