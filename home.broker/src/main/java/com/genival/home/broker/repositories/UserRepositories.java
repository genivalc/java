package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User, Long> {
}
