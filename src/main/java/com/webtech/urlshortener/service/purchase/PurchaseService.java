package com.webtech.urlshortener.service.purchase;

import com.webtech.urlshortener.repository.PurchaseRepository;
import com.webtech.urlshortener.repository.entity.PurchaseEntity;

public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public void registerReceipt(int userId, PurchaseReceipt receipt) {
        // todo go to external service to check if receipt is valid
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setRecipientId(userId);
        purchase.setReceiptId(receipt.id);
        repository.save(purchase);
    }
}
