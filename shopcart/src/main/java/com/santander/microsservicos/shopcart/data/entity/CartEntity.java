package com.santander.microsservicos.shopcart.data.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @GeneratedValue(generator = RandomUUIDGenerator.generatorName)
    @GenericGenerator(name = RandomUUIDGenerator.generatorName, strategy = "com.santander.microsservicos.shopcart.data.entity" +
            ".RandomUUIDGenerator")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime expires;

    @OneToMany(targetEntity = CartProductEntity.class, fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private List<CartProductEntity> products;
}
