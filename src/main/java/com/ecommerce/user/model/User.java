package com.ecommerce.user.model;

import com.ecommerce.cart.Cart;
import com.ecommerce.order.Order;
import com.ecommerce.product.model.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlist = new HashSet<>();

    @OneToMany(mappedBy = "orderBy")
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Cart cart;

    public String getFullname(){
        return lastName + " " + firstName;
    }

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
