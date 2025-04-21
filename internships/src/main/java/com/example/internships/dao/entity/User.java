package com.example.internships.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;
    @Getter
    private String firstName;   // Added
    private String lastName;    // Added

    @Setter
    @Getter
    @Column(unique = true, nullable = false)
    private String email;
    @Setter
    private String password;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;


    @Setter
    @Getter
    @ManyToMany
    private Set<Pack> packs = new HashSet<>();

    public enum Role {
        ADMIN,
        ENTREPRISE,
        ETUDIANT;

        public String getAuthority() {
            return "ROLE_" + this.name();
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // === Implémentation de UserDetails ===

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
