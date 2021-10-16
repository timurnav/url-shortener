package com.webtech.urlshortener.repository.entity;

import java.util.Date;

public class PurchaseEntity {

    private int recipientId;
    private String receiptId;
    private Date created;

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
