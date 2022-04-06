package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
//        Count each product occurrences, create receipt entries
        basket.getProducts().stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .forEach((product, count) -> receiptEntries.add(new ReceiptEntry(product, Math.toIntExact(count))));

        return new Receipt(receiptEntries);
    }
}
