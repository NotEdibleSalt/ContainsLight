package com.ss.contains.light.repository;

import com.ss.contains.light.dos.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author NotEdibleSalt
 * @version 1.0
 * @date 2022-5-2
 */
public interface UserRepository extends R2dbcRepository<User, Integer> {
}
