package com.pgichure.samples.spring_boot_rest_project.repos;

import com.pgichure.samples.spring_boot_rest_project.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {
}
