package com.webtech.urlshortener.service.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PurchaseReceipt {

    public final String id;

    @JsonCreator
    public PurchaseReceipt(String id) {
        this.id = id;
    }
}
