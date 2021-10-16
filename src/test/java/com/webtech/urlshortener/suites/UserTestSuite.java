package com.webtech.urlshortener.suites;

import com.webtech.urlshortener.repository.entity.UserEntity;
import com.webtech.urlshortener.repository.entity.UserRole;
import com.webtech.urlshortener.service.user.CreateUserTO;
import com.webtech.urlshortener.service.user.UserDataUpdateTO;
import com.webtech.urlshortener.service.user.UserTO;
import com.webtech.urlshortener.web.ResponseError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTestSuite extends BaseTestSuite {

    public static final CreateUserTO CREATE_ADMIN_TO = new CreateUserTO("Admin Name", "admin@gmail.com", "Password1!", true);

    @Test
    void userListIsEmptyWhenNothingCreatedTest() throws Exception {
        mockMvc.perform(get("/users").accept(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void notFoundTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", 123).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        ResponseError responseError = fromResponse(mvcResult, ResponseError.class);
        assertThat(responseError.errorMessage)
                .isEqualTo("User by user id '123' not found");
    }

    @Test
    void createUserTest() throws Exception {
        userListIsEmptyWhenNothingCreatedTest();

        MvcResult result = mockMvc.perform(post("/users").content(asJson(CREATE_ADMIN_TO))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserTO userFromResponse = fromResponse(result, UserTO.class);

        assertThat(userFromResponse.name).isEqualTo(CREATE_ADMIN_TO.name);
        assertThat(userFromResponse.email).isEqualTo(CREATE_ADMIN_TO.email);
        assertThat(userFromResponse.urlsCreated).isEqualTo(0);
        assertThat(userFromResponse.maxUrls).isGreaterThan(0);

        MvcResult resultAfterSave = mockMvc.perform(get("/users").accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        UserTO[] allUsersAfterSave = fromResponse(resultAfterSave, UserTO[].class);
        assertThat(allUsersAfterSave).hasSize(1);
        Assertions.assertThat(allUsersAfterSave[0])
                .usingRecursiveComparison()
                .isEqualTo(userFromResponse);

        UserEntity savedUser = getUser(userFromResponse.id);
        assertThat(savedUser.getPassword()).isEqualTo(CREATE_ADMIN_TO.password);
        assertThat(savedUser.getRoles()).contains(UserRole.ADMIN);
    }

    @Test
    void getUserByIdTest() throws Exception {
        userListIsEmptyWhenNothingCreatedTest();

        UserEntity user = createUser(builder -> {
            builder.setMaxUrls(2);
            builder.setUrlsCreated(1);
        });

        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", user.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserTO userTO = fromResponse(mvcResult, UserTO.class);
        Assertions.assertThat(userTO)
                .usingRecursiveComparison()
                .isEqualTo(new UserTO(user.getId(), user.getName(), user.getEmail(), 1, 2));
    }

    @Test
    void deleteUserTest() throws Exception {
        userListIsEmptyWhenNothingCreatedTest();
        UserEntity user = createUser();

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void updateUserTest() throws Exception {
        userListIsEmptyWhenNothingCreatedTest();
        UserEntity user = createUser();

        UserDataUpdateTO update = new UserDataUpdateTO("New name", "another@gmail.com");
        MvcResult result = mockMvc.perform(put("/users/{id}", user.getId())
                        .content(asJson(update))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andReturn();
        UserTO userFromResponse = fromResponse(result, UserTO.class);

        assertThat(userFromResponse.name).isEqualTo(update.name);
        assertThat(userFromResponse.email).isEqualTo(update.email);
        assertThat(userFromResponse.urlsCreated).isEqualTo(user.getUrlsCreated());
        assertThat(userFromResponse.maxUrls).isEqualTo(user.getMaxUrls());

        MvcResult resultAfterUpdate = mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        UserTO[] allUsersAfterSave = fromResponse(resultAfterUpdate, UserTO[].class);

        assertThat(allUsersAfterSave).hasSize(1);
        assertThat(allUsersAfterSave[0])
                .usingRecursiveComparison()
                .isEqualTo(userFromResponse);
    }
}
