package com.santander.microsservicos.shopcatalog.data.repository;

import com.santander.microsservicos.shopcatalog.data.entity.ShopItemEntity;
import com.santander.microsservicos.shopcatalog.data.enums.ShopItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItemEntity, Long> {
    List<ShopItemEntity> findByNameContainingIgnoreCase(String name);

    List<ShopItemEntity> findByCategory(ShopItemCategory category);
}
