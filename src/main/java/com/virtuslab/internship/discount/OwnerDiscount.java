package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;
import java.util.List;

public class OwnerDiscount extends TenPercentDiscount{
    public static String NAME = "OwnerDiscount";
    @Override
    public Receipt apply(Receipt receipt) {
        Receipt discounted = new Receipt(receipt.entries());
//      The 15 percent discount
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            discounted = new Receipt(receipt.entries(), discounts, totalPrice);
        }
//      The 10 percent discount
        discounted = super.apply(discounted);
        return discounted;
    }

    private boolean shouldApply(Receipt receipt) {
        int grainCount = 0;
        for (ReceiptEntry entry: receipt.entries()) {
            if (entry.product().getType() == Product.Type.GRAINS)
                grainCount+=entry.quantity();
        }
        return grainCount >= 3;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
