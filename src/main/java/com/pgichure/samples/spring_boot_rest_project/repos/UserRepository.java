package com.pgichure.samples.spring_boot_rest_project.repos;

import com.pgichure.samples.spring_boot_rest_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

}
