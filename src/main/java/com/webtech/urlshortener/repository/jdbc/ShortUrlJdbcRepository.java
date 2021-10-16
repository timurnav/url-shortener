package com.webtech.urlshortener.repository.jdbc;

import com.webtech.urlshortener.repository.ShortUrlRepository;
import com.webtech.urlshortener.repository.entity.ShortUrlEntity;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

@Repository
public class ShortUrlJdbcRepository implements ShortUrlRepository {

    private static final BeanPropertyRowMapper<ShortUrlEntity> ROW_MAPPER = newInstance(ShortUrlEntity.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ShortUrlJdbcRepository(JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public String findByShortUrl(String shortUrl) {
        List<ShortUrlEntity> query =
                jdbcTemplate.query("SELECT * FROM urls WHERE short_url=?", ROW_MAPPER, shortUrl);
        ShortUrlEntity found = DataAccessUtils.singleResult(query);
        if (found == null) {
            return null;
        }
        return found.getLongUrl();
    }

    @Override
    public ShortUrlEntity save(ShortUrlEntity toSave) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(toSave);
        namedParameterJdbcTemplate.update("INSERT INTO urls " +
                "(owner_id, long_url, short_url) VALUES " +
                "(:ownerId, :longUrl, :shortUrl)", parameterSource, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        toSave.setId((Integer) keys.get("id"));
        return toSave;
    }

    @Override
    public void remove(int urlId) {
        jdbcTemplate.update("DELETE FROM urls WHERE id=?", urlId);
    }
}
