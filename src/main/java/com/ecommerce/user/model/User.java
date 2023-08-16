package com.ecommerce.user.model;

import com.ecommerce.blog.model.Blog;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ADMIN;

    private boolean isEnable = true;
    private boolean isNonLocked = true;

    private String resetPasswordOTP;
    private LocalDateTime resetPasswordExpired;

    

    @ManyToMany(mappedBy = "likes", fetch = FetchType.EAGER)
    private Set<Blog> likedBlogs = new HashSet<>();

    @ManyToMany(mappedBy = "dislikes", fetch = FetchType.EAGER)
    private Set<Blog> dislikedBlogs = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
