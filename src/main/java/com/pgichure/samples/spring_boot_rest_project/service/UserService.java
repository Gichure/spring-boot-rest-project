package com.pgichure.samples.spring_boot_rest_project.service;

import com.pgichure.samples.spring_boot_rest_project.domain.Booking;
import com.pgichure.samples.spring_boot_rest_project.domain.User;
import com.pgichure.samples.spring_boot_rest_project.model.UserDTO;
import com.pgichure.samples.spring_boot_rest_project.repos.BookingRepository;
import com.pgichure.samples.spring_boot_rest_project.repos.UserRepository;
import com.pgichure.samples.spring_boot_rest_project.util.NotFoundException;
import com.pgichure.samples.spring_boot_rest_project.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public UserService(final UserRepository userRepository,
            final BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPssword(user.getPssword());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setPssword(userDTO.getPssword());
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Booking bookingIdBooking = bookingRepository.findFirstByBookingId(user);
        if (bookingIdBooking != null) {
            referencedWarning.setKey("user.booking.bookingId.referenced");
            referencedWarning.addParam(bookingIdBooking.getId());
            return referencedWarning;
        }
        return null;
    }

}
