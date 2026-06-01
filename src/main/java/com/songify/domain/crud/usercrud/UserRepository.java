package com.songify.domain.crud.usercrud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<UserEntity, Long> {
    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity save(UserEntity user);
    boolean existsByEmail(String email);
}
