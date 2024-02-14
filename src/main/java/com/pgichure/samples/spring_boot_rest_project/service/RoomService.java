package com.pgichure.samples.spring_boot_rest_project.service;

import com.pgichure.samples.spring_boot_rest_project.domain.Booking;
import com.pgichure.samples.spring_boot_rest_project.domain.Room;
import com.pgichure.samples.spring_boot_rest_project.model.RoomDTO;
import com.pgichure.samples.spring_boot_rest_project.repos.BookingRepository;
import com.pgichure.samples.spring_boot_rest_project.repos.RoomRepository;
import com.pgichure.samples.spring_boot_rest_project.util.NotFoundException;
import com.pgichure.samples.spring_boot_rest_project.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomService(final RoomRepository roomRepository,
            final BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<RoomDTO> findAll() {
        final List<Room> rooms = roomRepository.findAll(Sort.by("id"));
        return rooms.stream()
                .map(room -> mapToDTO(room, new RoomDTO()))
                .toList();
    }

    public RoomDTO get(final Long id) {
        return roomRepository.findById(id)
                .map(room -> mapToDTO(room, new RoomDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoomDTO roomDTO) {
        final Room room = new Room();
        mapToEntity(roomDTO, room);
        return roomRepository.save(room).getId();
    }

    public void update(final Long id, final RoomDTO roomDTO) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roomDTO, room);
        roomRepository.save(room);
    }

    public void delete(final Long id) {
        roomRepository.deleteById(id);
    }

    private RoomDTO mapToDTO(final Room room, final RoomDTO roomDTO) {
        roomDTO.setId(room.getId());
        roomDTO.setRoomName(room.getRoomName());
        roomDTO.setRoomNumber(room.getRoomNumber());
        roomDTO.setCapacity(room.getCapacity());
        return roomDTO;
    }

    private Room mapToEntity(final RoomDTO roomDTO, final Room room) {
        room.setRoomName(roomDTO.getRoomName());
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setCapacity(roomDTO.getCapacity());
        return room;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Room room = roomRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Booking rootIdBooking = bookingRepository.findFirstByRootId(room);
        if (rootIdBooking != null) {
            referencedWarning.setKey("room.booking.rootId.referenced");
            referencedWarning.addParam(rootIdBooking.getId());
            return referencedWarning;
        }
        return null;
    }

}
