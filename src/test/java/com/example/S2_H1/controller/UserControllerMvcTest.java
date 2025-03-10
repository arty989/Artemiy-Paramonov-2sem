package com.example.S2_H1.controller;

import com.example.S2_H1.request.user.UserCreateRequest;
import com.example.S2_H1.request.user.UserUpdateNameRequest;
import com.example.S2_H1.response.user.UserIdResponse;
import com.example.S2_H1.response.user.UserResponse;
import com.example.S2_H1.service.UserService;
import com.example.S2_H1.service.exception.NoSuchUserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("no-db-test")
@WebMvcTest(controllers = UserController.class)
public class UserControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void whenGetNewUser_thenReturnUserId() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest("Artemiy", "12345678", "mts@yandex.ru");
        UserIdResponse userId = new UserIdResponse(1L);

        when(userService.registerUser(any(UserCreateRequest.class))).thenReturn(userId);

        String userJson = "{\"userName\":\"Artemiy\",\"userPassword\":\"12345678\", \"userEmail\":\"mts@yandex.ru\"}";

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    public void whenGetNewUserName_thenReturnUpdatedUser() throws Exception {
      UserResponse user = new UserResponse();
      user.setUserId(1L);
      user.setUserName("Ivan");
      user.setUserPassword("12345678");
      user.setUserEmail("mts@yandex.ru");

      when(userService.updateUserName(any(UserUpdateNameRequest.class), any(Long.class))).thenReturn(user);

      String userJson = "{\"newUserName\":\"Ivan\"}";

      mockMvc.perform(patch("/api/users/rename/{userId}", 1)
      .contentType("application/json")
      .content(userJson))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId").value(1))
      .andExpect(jsonPath("$.userName").value("Ivan"));
    }

    @Test
    public void whenDeleteWithInvalidUserID_thenReturns404() throws Exception {
      Long nonExistentId = 999L;

      doThrow(new NoSuchUserException("Пользователь с ID " + nonExistentId + " не найден")).when(userService).deleteUser(any(Long.class));

      mockMvc.perform(delete("/api/users/delete/{userId}", nonExistentId))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$").value("Пользователь с ID 999 не найден"));
    }
}
