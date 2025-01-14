package com.example.authservice.controller;

import com.example.authservice.security.JwtResponse;
import com.example.authservice.security.RefreshJwtRequest;
import com.example.authservice.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/getNewAccessToken")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = jwtService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = jwtService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validateAccessToken")
    public ResponseEntity validateAccessToken(@RequestParam String accessToken) {
        return ResponseEntity.ok(jwtService.validateAccessToken(accessToken));
    }

    @PostMapping("/validateRefreshToken")
    public ResponseEntity validateRefreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(jwtService.validateRefreshToken(refreshToken));
    }

    @PostMapping("/decodeAccessToken")
    public ResponseEntity<String> decodeAccessToken(@RequestParam String accessToken) {
        return ResponseEntity.ok(jwtService.decodeToken(accessToken));
    }

    @PostMapping("/jwtTokenAlive")
    public ResponseEntity<?> isJwtExpired(@RequestParam String accessToken) {
        if(jwtService.isJwtExpired(accessToken)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok(true);
        }
    }


}
