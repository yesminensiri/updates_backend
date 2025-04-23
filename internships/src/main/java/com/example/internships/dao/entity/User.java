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
    private String lastName;
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
    private String birthDate; // Added field

    @Setter
    @Getter
    private String sex; // Added field

    @Setter
    @Getter
    private String university; // Added field

    @Setter
    @Getter
    private String domain; // Added field

    @Setter
    @Getter
    private String diplomas; // Added field

    @Setter
    @Getter
    private String address; // Added field

    @Setter
    @Getter
    private String phoneNumber; // Added field

    @Setter
    @Getter
    private String secondaryEmail; // Added field

    @Setter
    @Getter
    private String cv; // Added field

    @Setter
    @Getter
    private String profilePicture; // Added field

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


    public void setUsername(String username) {
        this.email = username;  // Since getUsername() uses email, set it here
    }


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
