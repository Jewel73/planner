package com.jtech.planner.repository;

import com.jtech.planner.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jewel
 * @version {1.0}
 */

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByUserName(String userName);
}
