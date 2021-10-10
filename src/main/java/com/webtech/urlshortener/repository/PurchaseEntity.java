package com.webtech.urlshortener.repository;

public class PurchaseEntity {

    private int recipient;
    private String receiptId;

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptId() {
        return receiptId;
    }
}
