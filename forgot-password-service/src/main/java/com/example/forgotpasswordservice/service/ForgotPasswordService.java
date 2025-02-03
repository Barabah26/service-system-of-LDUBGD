package com.example.forgotpasswordservice.service;

import com.example.forgotpasswordservice.dto.ForgotPasswordDto;
import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;
import com.ldubgd.components.dao.enums.StatementStatus;

import java.util.List;

public interface ForgotPasswordService {
    void createForgotPasswordStatement(ForgotPasswordRequestDto forgotPasswordRequestDto);
    List<ForgotPasswordDto> getForgotPasswordInfoByStatus(StatementStatus status);
    void updateForgotPasswordStatus(Long forgotPasswordId, StatementStatus status);

}
