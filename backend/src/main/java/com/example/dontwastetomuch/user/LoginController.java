package com.example.dontwastetomuch.user;


import com.example.dontwastetomuch.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserService userService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginData loginData) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
            MyUser user = userService.findByUsername(loginData.getUsername()).orElseThrow();
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", user.getRoles());
            String jwt = jwtService.createJwt(claims, user.getId());
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

