package com.webtech.urlshortener.web.controller;

import com.webtech.urlshortener.service.purchase.PurchaseReceipt;
import com.webtech.urlshortener.service.purchase.UserPurchaseService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("purchases")
public class PurchaseController {

    private final UserPurchaseService purchaseService;

    public PurchaseController(UserPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PutMapping("/{userId}/premium")
    public void premium(@PathVariable int userId,
                        @Valid @RequestBody PurchaseReceipt receipt) {
        purchaseService.processPremiumReceipt(userId, receipt);
    }
}
