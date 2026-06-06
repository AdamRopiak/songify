package com.songify.domain.usercrud;

import com.songify.domain.crud.util.BaseEnity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEnity {

    @Id
    @GeneratedValue(generator = "users_userid_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "users_userid_seq",
            sequenceName = "users_userid_seq",
            allocationSize = 1
    )
    @Column(name="userid")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private boolean enabled = true;

    private Collection<String> authorities = new HashSet<>();

    public UserEntity(String email, String password, boolean enabled, Collection<String> authorities) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }
}
