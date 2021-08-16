package com.santander.microsservicos.shopcatalog.data.dto;

import com.santander.microsservicos.shopcatalog.data.enums.ShopItemCategory;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemDTO {
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private ShopItemCategory category;

    private String description;

    @NotNull
    @NotEmpty
    private String thumbnail;

    private List<String> images;

    @Min(0)
    @Max(1000000)
    private float price = 0.0F;

    private boolean available;
}
