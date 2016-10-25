package com.tenPines.persistence;

import com.tenPines.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Kevin on 21/10/16.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByUserName(String userName);
}
