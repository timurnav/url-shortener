package com.webtech.urlshortener.repository.inmem;

import com.webtech.urlshortener.repository.PurchaseRepository;
import com.webtech.urlshortener.repository.entity.PurchaseEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PurchaseInMemRepository implements PurchaseRepository {

    private final Map<String, PurchaseEntity> purchases = new HashMap<>();

    @Override
    public void save(PurchaseEntity purchase) {
        purchases.put(purchase.getReceiptId(), purchase);
    }
}
