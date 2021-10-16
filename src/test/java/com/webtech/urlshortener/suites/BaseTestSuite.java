package com.webtech.urlshortener.suites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.webtech.urlshortener.configuration.Resettable;
import com.webtech.urlshortener.repository.UserRepository;
import com.webtech.urlshortener.repository.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.function.Consumer;

@SpringBootTest(classes = TestSuiteConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("in-memory")
public abstract class BaseTestSuite {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new ParameterNamesModule());

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<Resettable> resettables;

    @AfterEach
    void clearUp() {
        resettables.forEach(Resettable::reset);
    }

    protected UserEntity createUser() {
        return createUser(userEntity -> {
        });
    }

    protected UserEntity createUser(Consumer<UserEntity> builder) {
        UserEntity user = new UserEntity();
        user.setName("Name");
        user.setEmail("test@test.com");
        user.setPassword("Strong1?");
        builder.accept(user);
        return userRepository.save(user);
    }

    protected UserEntity getUser(int id) {
        return userRepository.getById(id);
    }

    public String asJson(Object object) throws Exception {
        return OBJECT_MAPPER
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return OBJECT_MAPPER
                .readerFor(type)
                .readValue(result.getResponse().getContentAsString());
    }

    public <T> T fromJson(String json, Class<T> type) throws Exception {
        return OBJECT_MAPPER
                .readerFor(type)
                .readValue(json);
    }
}
