package com.webtech.urlshortener.repository.jdbc;

import com.webtech.urlshortener.repository.PurchaseRepository;
import com.webtech.urlshortener.repository.entity.PurchaseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseJdbcRepository implements PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    public PurchaseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PurchaseEntity purchase) {
        jdbcTemplate.update("INSERT INTO purchases (recipient_id, receipt_id) VALUES ("
                + purchase.getRecipientId() + ", '" + purchase.getReceiptId() + "')");
    }
}
