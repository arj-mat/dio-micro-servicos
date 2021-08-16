package com.santander.microsservicos.shopcart.data.mapper;

import com.santander.microsservicos.shopcart.data.dto.CartDTO;
import com.santander.microsservicos.shopcart.data.dto.CartProductDTO;
import com.santander.microsservicos.shopcart.data.entity.CartEntity;
import com.santander.microsservicos.shopcart.data.entity.CartProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartProductMapper {
    CartProductMapper INSTANCE = Mappers.getMapper( CartProductMapper.class );

    CartProductEntity toModel(CartProductDTO dto);

    CartProductDTO toDTO(CartProductEntity entity);
}
