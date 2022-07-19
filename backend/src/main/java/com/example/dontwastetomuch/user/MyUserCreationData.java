package com.example.dontwastetomuch.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserCreationData {

    private String username;
    private String password;
    private String passwordRepeat;

}
