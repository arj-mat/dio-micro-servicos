package com.santander.microsservicos.shopcart.data.service;

import com.santander.microsservicos.shopcart.data.dto.CartDTO;
import com.santander.microsservicos.shopcart.data.dto.CheckoutDTO;
import com.santander.microsservicos.shopcart.data.entity.CartEntity;
import com.santander.microsservicos.shopcart.data.entity.CartProductEntity;
import com.santander.microsservicos.shopcart.data.mapper.CartMapper;
import com.santander.microsservicos.shopcart.data.mapper.CartProductMapper;
import com.santander.microsservicos.shopcart.data.repository.CartRepository;
import com.santander.microsservicos.shopcart.external.ExternalResult;
import com.santander.microsservicos.shopcart.external.RequestProductFromCatalog;
import com.santander.microsservicos.shopcart.external.model.ProductFromCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    private final CartMapper cartMapper = CartMapper.INSTANCE;
    private final CartProductMapper cartProductMapper = CartProductMapper.INSTANCE;

    private void refreshCartExpireDate(CartEntity cart) {
        cart.setExpires(
                LocalDateTime
                        .now()
                        .plusHours( 24 )
        );
    }

    /**
     * Retorna o carrinho especificado pelo ID. Caso ele não exista ou tenha expirado, cria e retorna um novo carrinho.
     *
     * @param id (Opcional) ID do carrinho
     * @return CartDTO
     */
    public CartEntity getOrCreateValidCartEntity(Optional<String> id) {
        if ( id.isPresent() ) {
            Optional<CartEntity> existingCart = this.cartRepository.findById( id.get() );

            if ( existingCart.isPresent() ) {
                // O carrinho existe

                if ( existingCart.get().getExpires().isAfter( LocalDateTime.now() ) ) {
                    // O carrinho ainda é válido
                    return existingCart.get();
                } else {
                    // O carrinho expirou e deve ser deletado
                    this.cartRepository.delete( existingCart.get() );
                }
            }
        }

        // Cria e retorna um novo carrinho
        return this.createCart();
    }

    public CartDTO getOrCreateValidCartDTO(Optional<String> id) {
        CartEntity entity = this.getOrCreateValidCartEntity( id );
        CartDTO mappedDTO = this.cartMapper.toDTO( entity );

        return mappedDTO;
    }

    public CartEntity createCart() {
        return this.cartRepository.save(
                CartEntity
                        .builder()
                        .products( new ArrayList<>() )
                        .expires( LocalDateTime.now().plusHours( 24 ) )
                        .build()
        );
    }

    public CartDTO insertProduct(String cartID, Long productID) {
        CartEntity cart = this.getOrCreateValidCartEntity( Optional.ofNullable( cartID ) );

        // Obter os detalhes do produto pela API do shop-catalog:
        ExternalResult<ProductFromCatalog> productFromCatalog = new RequestProductFromCatalog().byID( productID );

        if ( productFromCatalog.data.isEmpty() ) {
            return this.cartMapper
                    .toDTO( cart )
                    .setAlertMessage( "Houve um erro ao processar o produto escolhido." );
        } else if ( !productFromCatalog.data.get().isAvailable() ) {
            return this.cartMapper
                    .toDTO( cart )
                    .setAlertMessage(
                            String.format(
                                    "O produto \"%s\" não está disponível.",
                                    productFromCatalog.data.get().getName()
                            )
                    );
        }

        List<CartProductEntity> products = cart.getProducts();

        Optional<CartProductEntity> productAlreadyInCart = products
                .stream()
                .filter( item -> item.getProductID() == productID )
                .findFirst();

        if ( productAlreadyInCart.isPresent() ) {
            productAlreadyInCart.get().setAmount( productAlreadyInCart.get().getAmount() + 1 );
        } else {
            products.add(
                    CartProductEntity
                            .builder()
                            .productID( productID )
                            .amount( 1 )
                            .name( productFromCatalog.data.get().getName() )
                            .price( productFromCatalog.data.get().getPrice() )
                            .thumbnail( productFromCatalog.data.get().getThumbnail() )
                            .build()
            );
        }

        this.cartRepository.save( cart );

        return this.cartMapper.toDTO( cart );
    }

    public CartDTO clearCart(String cartID) {
        CartEntity cart = this.cartRepository.findById( cartID ).orElse( this.createCart() );

        cart.setProducts( new ArrayList<>() );

        this.refreshCartExpireDate( cart );

        return this.cartMapper.toDTO( this.cartRepository.save( cart ) );
    }

    public CartDTO removeProduct(String cartID, Long productID) {
        CartEntity cart = this.cartRepository.findById( cartID ).orElse( this.createCart() );

        cart.getProducts().removeIf( item -> item.getProductID() == productID );

        this.refreshCartExpireDate( cart );

        return this.cartMapper.toDTO( this.cartRepository.save( cart ) );
    }

    public CartDTO updateProductAmount(String cartID, Long productID, Integer newAmount) {
        CartEntity cart = this.cartRepository.findById( cartID ).orElse( this.createCart() );

        cart.setProducts(
                cart
                        .getProducts()
                        .stream()
                        .peek( item -> {
                            if ( item.getProductID() == productID ) {
                                item.setAmount( newAmount );
                            }
                        } )
                        .collect( Collectors.toList() )
        );

        this.refreshCartExpireDate( cart );

        return this.cartMapper.toDTO( this.cartRepository.save( cart ) );
    }

    public CheckoutDTO getCheckoutInfo(String cartID) {
        CartEntity cart = this.cartRepository.findById( cartID ).orElse( this.createCart() );

        List<Long> finalProductList = new ArrayList<>();

        for ( CartProductEntity productEntity : cart.getProducts() ) {
            for ( int i = 0; i < productEntity.getAmount(); i++ ) {
                finalProductList.add( productEntity.getProductID() );
            }
        }

        return CheckoutDTO
                .builder()
                .products( finalProductList )
                .totalPrice(
                        cart
                                .getProducts()
                                .stream()
                                .map( item -> item.getPrice() * item.getAmount() )
                                .reduce( 0.0F, Float::sum )
                )
                .build();
    }
}
