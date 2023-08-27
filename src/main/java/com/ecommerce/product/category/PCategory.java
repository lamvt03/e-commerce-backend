package com.ecommerce.product.category;


import com.ecommerce.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_category")
public class PCategory extends AbstractEntity {

    @Column(unique = true)
    private String name;
}
