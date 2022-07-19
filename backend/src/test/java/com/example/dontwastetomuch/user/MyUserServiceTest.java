package com.example.dontwastetomuch.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class MyUserServiceTest {

    @Test
    void shouldCreateNewUser() {
        // Given
        MyUserRepo userRepository = Mockito.mock(MyUserRepo.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        MyUserCreationData userCreationData = new MyUserCreationData("testUser", "password", "password");
        MyUserService userService = new MyUserService(userRepository, passwordEncoder);

        // when
        userService.createNewUser(userCreationData);

        // then
        MyUser expectedUser = new MyUser();
        expectedUser.setUsername("testUser");
        expectedUser.setPassword("hashedPassword");

        Mockito.verify(userRepository).save(expectedUser);
    }

    @Test
    void shouldNotCreateNewUserPasswordsDoNotMatch() {
        // Given
        MyUserCreationData userCreationData = new MyUserCreationData("hans", "123", "122");
        MyUserService userService = new MyUserService(null, null);

        // when
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.createNewUser(userCreationData))
                .withMessage("passwords do not match");
    }

    @Test
    void shouldNotCreateNewUserUsernameIsBlank() {
        // Given
        MyUserCreationData userCreationData = new MyUserCreationData(" ", "123", "123");
        MyUserService userService = new MyUserService(null, null);

        // when
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.createNewUser(userCreationData))
                .withMessage("username is blank");
    }

    @Test
    void shouldNotCreateNewUserUsernameIsNull() {
        // Given
        MyUserCreationData userCreationData = new MyUserCreationData(null, "123", "123");
        MyUserService userService = new MyUserService(null, null);

        // when
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.createNewUser(userCreationData))
                .withMessage("username is blank");
    }

    @Test
    void shouldNotCreateNewUserPasswordsAreNull() {
        // Given
        MyUserCreationData userCreationData = new MyUserCreationData("hans", null, null);
        MyUserService userService = new MyUserService(null, null);

        // when
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> userService.createNewUser(userCreationData));
    }

}