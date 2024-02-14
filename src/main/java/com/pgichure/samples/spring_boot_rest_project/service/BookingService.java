package com.pgichure.samples.spring_boot_rest_project.service;

import com.pgichure.samples.spring_boot_rest_project.domain.Booking;
import com.pgichure.samples.spring_boot_rest_project.domain.Room;
import com.pgichure.samples.spring_boot_rest_project.domain.User;
import com.pgichure.samples.spring_boot_rest_project.model.BookingDTO;
import com.pgichure.samples.spring_boot_rest_project.repos.BookingRepository;
import com.pgichure.samples.spring_boot_rest_project.repos.RoomRepository;
import com.pgichure.samples.spring_boot_rest_project.repos.UserRepository;
import com.pgichure.samples.spring_boot_rest_project.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(final BookingRepository bookingRepository,
            final UserRepository userRepository, final RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public List<BookingDTO> findAll() {
        final List<Booking> bookings = bookingRepository.findAll(Sort.by("id"));
        return bookings.stream()
                .map(booking -> mapToDTO(booking, new BookingDTO()))
                .toList();
    }

    public BookingDTO get(final Long id) {
        return bookingRepository.findById(id)
                .map(booking -> mapToDTO(booking, new BookingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BookingDTO bookingDTO) {
        final Booking booking = new Booking();
        mapToEntity(bookingDTO, booking);
        return bookingRepository.save(booking).getId();
    }

    public void update(final Long id, final BookingDTO bookingDTO) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookingDTO, booking);
        bookingRepository.save(booking);
    }

    public void delete(final Long id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO mapToDTO(final Booking booking, final BookingDTO bookingDTO) {
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setBookingId(booking.getBookingId() == null ? null : booking.getBookingId().getId());
        bookingDTO.setRootId(booking.getRootId() == null ? null : booking.getRootId().getId());
        return bookingDTO;
    }

    private Booking mapToEntity(final BookingDTO bookingDTO, final Booking booking) {
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        final User bookingId = bookingDTO.getBookingId() == null ? null : userRepository.findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new NotFoundException("bookingId not found"));
        booking.setBookingId(bookingId);
        final Room rootId = bookingDTO.getRootId() == null ? null : roomRepository.findById(bookingDTO.getRootId())
                .orElseThrow(() -> new NotFoundException("rootId not found"));
        booking.setRootId(rootId);
        return booking;
    }

}
