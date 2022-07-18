package com.example.dontwastetomuch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createNewUser(MyUserCreationData userCreationData) {
        if (!Objects.equals(userCreationData.getPassword(), userCreationData.getPasswordRepeat())) {
            throw new IllegalArgumentException("passwords do not match");
        }

        if (userCreationData.getUsername() == null || userCreationData.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is blank");
        }

        MyUser user = new MyUser();
        user.setUsername(userCreationData.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationData.getPassword()));

        userRepository.save(user);
    }

    public Optional<MyUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<MyUser> findById(String userId) {
        return userRepository.findById(userId);
    }
}
