package com.webtech.urlshortener.service.purchase;

import com.webtech.urlshortener.repository.PurchaseEntity;
import com.webtech.urlshortener.repository.PurchaseRepository;

public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public void registerReceipt(int userId, PurchaseReceipt receipt) {
        // todo go to external service to check if receipt is valid
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setRecipient(userId);
        purchase.setReceiptId(receipt.id);
        repository.save(purchase);
    }
}
