package com.genival.home.broker.repositories;

import com.genival.home.broker.dto.user.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<UserDTO, Long> {
}
