package com.santander.microsservicos.shopcatalog.data.mapper;

import com.santander.microsservicos.shopcatalog.data.dto.ShopItemDTO;
import com.santander.microsservicos.shopcatalog.data.entity.ShopItemEntity;
import com.santander.microsservicos.shopcatalog.data.entity.ShopItemImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ShopItemMapper {
    ShopItemMapper INSTANCE = Mappers.getMapper( ShopItemMapper.class );

    @Mapping(target = "images", source = "images", qualifiedByName = "imageListToEntities")
    ShopItemEntity toModel(ShopItemDTO shopItemDTO);

    @Mapping(target = "images", source = "images", qualifiedByName = "imageEntitiesToList")
    ShopItemDTO toDTO(ShopItemEntity shopItemEntity);

    @Named("imageEntitiesToList")
    default List<String> imagesEntitiesToList(List<ShopItemImageEntity> list) {
        return list
                .stream()
                .map( ShopItemImageEntity::getUrl )
                .collect( Collectors.toList() );
    }

    @Named("imageListToEntities")
    default List<ShopItemImageEntity> imageListToEntities(List<String> list) {
        return list
                .stream()
                .map(
                        item -> ShopItemImageEntity
                                .builder()
                                .url( item )
                                .build()
                )
                .collect( Collectors.toList() );
    }
}
