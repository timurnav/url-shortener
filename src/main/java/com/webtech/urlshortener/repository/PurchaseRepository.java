package com.webtech.urlshortener.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PurchaseRepository {

    private final Map<String, PurchaseEntity> purchases = new HashMap<>();

    public void save(PurchaseEntity purchase) {
        purchases.put(purchase.getReceiptId(), purchase);
    }
}
