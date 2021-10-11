package com.webtech.urlshortener.repository;

import com.webtech.urlshortener.service.exceptions.UserNotFoundException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

@Repository
public class UserRepository {

    private static final BeanPropertyRowMapper<UserEntity> ROW_MAPPER = newInstance(UserEntity.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public UserEntity save(UserEntity user) {
        if (user.getId() == null) {
            return insert(user);
        }
        return update(user);
    }

    private UserEntity insert(UserEntity user) {
        // https://stackoverflow.com/questions/1665846/identity-from-sql-insert-via-jdbctemplate
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Number newKey = insertUser.executeAndReturnKey(parameterSource);
        user.setId(newKey.intValue());
        return user;
    }

    private UserEntity update(UserEntity user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        parameterSource.registerSqlType("created", Types.TIMESTAMP);
        int updated = namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password," +
                        " urls_created=:urlsCreated, max_urls=:maxUrls WHERE id=:id", parameterSource);
        if (updated == 0) {
            throw new UserNotFoundException(user.getId());
        }
        return user;
    }

    public boolean deleteById(int userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", userId) != 0;
    }

    public List<UserEntity> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }

    public UserEntity getById(int userId) {
        List<UserEntity> query = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, userId);
        return DataAccessUtils.singleResult(query);
    }
}
