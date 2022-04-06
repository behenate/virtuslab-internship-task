package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerDiscountTest {

    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50ButNotEnoughGrain() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new OwnerDiscount();
        var expectedTotalPrice = cheese.getPrice().add(steak.getPrice()).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApplyJust15PercentDiscountWhenThreeGrainProducts() {
        var productDb = new ProductDb();
        var cereal = productDb.getProduct("Cereals");

        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereal, 1));
        receiptEntries.add(new ReceiptEntry(cereal, 2));
        var receipt = new Receipt(receiptEntries);
        var discount = new OwnerDiscount();
        var expectedTotalPrice = cereal.getPrice().multiply(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(0.85));
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApplyBothDiscountsWhenPriceAbove50AndMoreThan3GrainProducts() {
        var productDb = new ProductDb();
        var cereal = productDb.getProduct("Cereals");
        var pork = productDb.getProduct("Pork");

        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereal, 3));
        receiptEntries.add(new ReceiptEntry(pork, 3));
        var receipt = new Receipt(receiptEntries);
        var discount = new OwnerDiscount();
        var priceAfterFirstDiscount = (cereal.getPrice().add(pork.getPrice())).multiply(BigDecimal.valueOf(3*0.85));
        var priceAfterBothDiscounts = priceAfterFirstDiscount.multiply(BigDecimal.valueOf(0.9));
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(priceAfterBothDiscounts, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApplyAnyDiscountWhenPriceIsBelow50AndNoGrain() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new TenPercentDiscount();
        var expectedTotalPrice = cheese.getPrice().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
