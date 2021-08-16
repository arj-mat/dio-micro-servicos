package com.santander.microsservicos.shopcart.controller;

import com.santander.microsservicos.shopcart.data.dto.CartDTO;
import com.santander.microsservicos.shopcart.data.dto.CheckoutDTO;
import com.santander.microsservicos.shopcart.data.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService service;

    @GetMapping(value = "/{cart}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> getCart(@PathVariable("cart") String id) {
        return ResponseEntity.ok( this.service.getOrCreateValidCartDTO( Optional.of( id ) ) );
    }

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> createCart() {
        return ResponseEntity.ok( this.service.getOrCreateValidCartDTO( Optional.empty() ) );
    }

    @GetMapping(value = "/{cart}/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> clearCart(@PathVariable("cart") String cartID) {
        return ResponseEntity.ok( this.service.clearCart( cartID ) );
    }

    @GetMapping(value = "/{cart}/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> insertItem(@PathVariable("cart") String cartID, @RequestParam("product_id") long productID) {
        return ResponseEntity.ok( this.service.insertProduct( cartID, productID ) );
    }

    @GetMapping(value = "/{cart}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> updateItemAmount(@PathVariable("cart") String cartID,
                                                    @RequestParam("product_id") long productID,
                                                    @RequestParam("amount") @Min(1) @Max(100) int amount) {
        return ResponseEntity.ok( this.service.updateProductAmount( cartID, productID, amount ) );
    }

    @GetMapping(value = "/{cart}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> removeItem(@PathVariable("cart") String cartID, @RequestParam("product_id") long productID) {
        return ResponseEntity.ok( this.service.removeProduct( cartID, productID ) );
    }

    @GetMapping(value = "/{cart}/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckoutDTO> getCheckoutInfo(@PathVariable("cart") String id) {
        return ResponseEntity.ok( this.service.getCheckoutInfo( id ) );
    }
}
