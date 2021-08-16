package com.santander.microsservicos.shopcatalog.controller;

import com.santander.microsservicos.shopcatalog.data.dto.ShopItemDTO;
import com.santander.microsservicos.shopcatalog.data.service.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class CatalogController {
    @Autowired
    private ShopItemService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShopItemDTO>> getAll() {
        return ResponseEntity.ok( this.service.getAll() );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopItemDTO> getByID(@PathVariable Long id) {
        Optional<ShopItemDTO> dto = this.service.getItemByID( id );

        return dto
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping(value = "/search", params = { "name" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShopItemDTO>> searchByName(@Param("name") String name) {
        return ResponseEntity.ok( this.service.searchByName( name ) );
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopItemDTO> insert(@RequestBody @Valid ShopItemDTO dto) {
        return ResponseEntity.ok( this.service.save( dto ) );
    }
}
