package com.example.dontwastetomuch.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserService {

    public final MyUserRepo myUserRepo;
    public void addNewUser(MyUser newMyUser) {
        myUserRepo.save(newMyUser);
    }


    public Optional<MyUser> findByUsername(String username) {
        return myUserRepo.findByUsername(username);
    }

}
