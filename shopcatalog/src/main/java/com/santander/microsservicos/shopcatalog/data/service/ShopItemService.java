package com.santander.microsservicos.shopcatalog.data.service;

import com.santander.microsservicos.shopcatalog.data.dto.ShopItemDTO;
import com.santander.microsservicos.shopcatalog.data.entity.ShopItemEntity;
import com.santander.microsservicos.shopcatalog.data.mapper.ShopItemMapper;
import com.santander.microsservicos.shopcatalog.data.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopItemService {
    @Autowired
    ShopItemRepository repository;

    private final ShopItemMapper mapper = ShopItemMapper.INSTANCE;

    @Transactional(readOnly = true)
    public List<ShopItemDTO> searchByName(String name) {
        return this.repository
                .findByNameContainingIgnoreCase( name )
                .stream()
                .map( this.mapper::toDTO )
                .collect( Collectors.toList() );
    }

    @Transactional(readOnly = true)
    public Optional<ShopItemDTO> getItemByID(Long id) {
        return this.repository
                .findById( id )
                .map( entity -> Optional.of( this.mapper.toDTO( entity ) ) )
                .orElseGet( Optional::empty );
    }

    @Transactional(readOnly = true)
    public List<ShopItemDTO> getAll() {
        return this.repository
                .findAll()
                .stream()
                .map( this.mapper::toDTO )
                .collect( Collectors.toList() );
    }

    public boolean itemExistsByID(Long id) {
        return this.repository.existsById( id );
    }

    @Transactional
    public ShopItemDTO save(ShopItemDTO dto) {
        ShopItemEntity entity = this.mapper.toModel( dto );

        ShopItemEntity savedEntity = this.repository.save( entity );

        return this.mapper.toDTO( savedEntity );
    }
}
