package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.repository.entity.PurchaseEntity;

public interface PurchaseRepository {
    void save(PurchaseEntity purchase);
}
