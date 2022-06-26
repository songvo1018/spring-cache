package com.nosov.cachelearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nosov.cachelearn.domain.User;

public interface UserRepository  extends JpaRepository<User, Long> {
}
