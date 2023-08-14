package com.ecommerce.blog.model;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Blog extends AbstractEntity {

    private String title;
    private String description;
    private String category;
    private int numViews;
    private boolean isLiked;
    private boolean isDisliked;

    @ManyToMany
    @JoinTable(
            name = "blog_like",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "blog_dislike",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes = new HashSet<>();
    private String image = "https://wiki.matbao.net/wp-content/uploads/2019/09/blog-la-gi.jpg";
    private String author = "Admin";
}
