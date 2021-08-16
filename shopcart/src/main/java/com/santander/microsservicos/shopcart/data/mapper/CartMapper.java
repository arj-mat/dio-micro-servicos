package com.santander.microsservicos.shopcart.data.mapper;

import com.santander.microsservicos.shopcart.data.dto.CartDTO;
import com.santander.microsservicos.shopcart.data.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper( CartMapper.class );

    CartEntity toModel(CartDTO dto);

    CartDTO toDTO(CartEntity entity);
}
