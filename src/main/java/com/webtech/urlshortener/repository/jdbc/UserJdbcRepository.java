package com.webtech.urlshortener.repository.jdbc;

import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.repository.entity.UserEntity;
import com.webtech.urlshortener.repository.entity.UserRole;
import com.webtech.urlshortener.service.exceptions.UserNotFoundException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

@Repository
public class UserJdbcRepository implements UserRepository {

    private static final BeanPropertyRowMapper<UserEntity> ROW_MAPPER = newInstance(UserEntity.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public UserEntity save(UserEntity user) {
        UserEntity saved = user.getId() == null
                ? insert(user)
                : update(user);
        actualizeRoles(saved);
        return saved;
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

    private void actualizeRoles(UserEntity user) {
        List<UserRole> roles = jdbcTemplate.queryForList(
                "SELECT role FROM user_roles WHERE user_id=?", UserRole.class, user.getId());
        HashSet<UserRole> existingRoles = new HashSet<>(roles);
        List<UserRole> rolesToInsert = user.getRoles().stream()
                .filter(role -> !existingRoles.contains(role))
                .collect(Collectors.toList());
        if (!rolesToInsert.isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, CAST(? AS user_role))",
                    rolesToInsert, rolesToInsert.size(),
                    (ps, role) -> {
                        ps.setInt(1, user.getId());
                        ps.setString(2, role.name());
                    });
        }
        List<UserRole> rolesToDelete = existingRoles.stream()
                .filter(role -> !user.getRoles().contains(role))
                .collect(Collectors.toList());
        if (!rolesToDelete.isEmpty()) {
            jdbcTemplate.batchUpdate("DELETE FROM user_roles WHERE user_id=? AND role =CAST(? AS user_role)",
                    rolesToDelete, rolesToDelete.size(),
                    (ps, role) -> {
                        ps.setInt(1, user.getId());
                        ps.setString(2, role.name());
                    });
        }
    }

    @Override
    public boolean deleteById(int userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", userId) != 0;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<UserRole>> roles = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                roles.computeIfAbsent(rs.getInt("user_id"), id -> new HashSet<>())
                        .add(UserRole.valueOf(rs.getString("role")));
            }
        });
        users.forEach(user -> user.getRoles().addAll(roles.getOrDefault(user.getId(), Set.of())));
        return users;
    }

    @Override
    public UserEntity getById(int userId) {
        List<UserEntity> query = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, userId);
        UserEntity userEntity = DataAccessUtils.singleResult(query);
        if (userEntity != null) {
            List<UserRole> roles =
                    jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", UserRole.class, userId);
            userEntity.getRoles().addAll(roles);
        }
        return userEntity;
    }
}
