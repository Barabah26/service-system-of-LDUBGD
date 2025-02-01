package com.example.forgotpasswordservice.service;

import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;

public interface ForgotPasswordService {
    void createForgotPasswordStatement(ForgotPasswordRequestDto forgotPasswordRequestDto);

}
