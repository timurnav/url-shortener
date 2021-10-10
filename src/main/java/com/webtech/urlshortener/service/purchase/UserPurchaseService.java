package com.webtech.urlshortener.service.purchase;

import com.webtech.urlshortener.service.user.PremiumGranted;
import com.webtech.urlshortener.service.user.UserService;

public class UserPurchaseService {

    private final UserService userService;
    private final PurchaseService purchaseService;

    public UserPurchaseService(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    public void processPremiumReceipt(int userId, PurchaseReceipt receipt) {
        purchaseService.registerReceipt(userId, receipt);
        userService.adjust(userId, new PremiumGranted());
    }
}
