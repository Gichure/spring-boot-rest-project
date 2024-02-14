package com.pgichure.samples.spring_boot_rest_project.repos;

import com.pgichure.samples.spring_boot_rest_project.domain.Booking;
import com.pgichure.samples.spring_boot_rest_project.domain.Room;
import com.pgichure.samples.spring_boot_rest_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findFirstByBookingId(User user);

    Booking findFirstByRootId(Room room);

}
