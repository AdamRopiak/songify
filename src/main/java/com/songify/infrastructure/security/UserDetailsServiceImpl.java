package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserEntity;
import com.songify.domain.usercrud.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

@AllArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsManager {

    private  static final String DEFAULT_USER_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public void createUser(UserDetails user) {
        if(userExists(user.getUsername())) {
            log.warn("User already exists");
            throw new RuntimeException("User already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity crateUser = new UserEntity(
                user.getUsername(),
                encodedPassword,
                true,
                List.of(DEFAULT_USER_ROLE)
        );
        UserEntity savedUser = userRepository.save(crateUser);
        log.info("User with ID: " + savedUser.getUserId() + " created.");

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }


}
