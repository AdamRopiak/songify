package com.songify.domain.usercrud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity save(UserEntity user);

    boolean existsByEmail(String email);
}
