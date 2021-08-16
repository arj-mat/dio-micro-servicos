package com.santander.microsservicos.shopcatalog.data.entity;

import com.santander.microsservicos.shopcatalog.data.enums.ShopItemCategory;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private ShopItemCategory category;

    @Column(nullable = false)
    private String description;

    private String thumbnail;

    @OneToMany(targetEntity = ShopItemImageEntity.class, fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private List<ShopItemImageEntity> images;

    @Column(nullable = false)
    private float price = 0.0F;

    @Column(nullable = false)
    private boolean available = false;
}
