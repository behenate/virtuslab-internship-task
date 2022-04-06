package com.virtuslab.internship.springboot;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.Discount;
import com.virtuslab.internship.discount.OwnerDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class BasketController {
    private final ProductDb productDb = new ProductDb();
    private final Basket cart;

    BasketController() {
        this.cart = new Basket();
    }
//    Add product by name from ProductDb
    @PostMapping("/addProductToCart")
    void addProduct(@RequestBody String productName) {
        cart.addProduct(productDb.getProduct(productName));
    }
//      Get the cart contents
    @GetMapping("/cart")
    public CollectionModel<EntityModel<Product>> getCart() {
        List<EntityModel<Product>> products = cart.getProducts().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(BasketController.class).getCart()).withRel("products")))
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(BasketController.class).getCart()).withSelfRel());
    }

//    Generate receipt with a discount
    @GetMapping("/generateReceipt/{discountName}")
    public EntityModel<Receipt> applyDiscount(@PathVariable String discountName) {
        var receiptGenerator = new ReceiptGenerator();
        var receipt =  receiptGenerator.generate(cart);
//        In a real application it would probably make more sense to create DiscountDb similar to ProductDb
        Discount[] discounts = {new TenPercentDiscount(), new OwnerDiscount()};
        for (Discount discount : discounts) {
            if (discount.getName().equals(discountName)) {
                System.out.println(receipt.entries().size());
                receipt = discount.apply(receipt);
                break;
            }
        }
        return EntityModel.of(receipt,
                linkTo(methodOn(BasketController.class).applyDiscount(discountName)).withSelfRel());
    }

//    Generate no discount receipt
    @GetMapping("/generateReceipt")
    public EntityModel<Receipt> generateReceipt() {
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        return EntityModel.of(receipt,
                linkTo(methodOn(BasketController.class).generateReceipt()).withSelfRel());
    }


}

