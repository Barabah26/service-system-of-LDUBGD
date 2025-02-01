package com.example.forgotpasswordservice.service.impl;

import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;
import com.example.forgotpasswordservice.exception.RecourseNotFoundException;
import com.example.forgotpasswordservice.repository.ForgotPasswordInfoRepository;
import com.example.forgotpasswordservice.repository.ForgotPasswordRepository;
import com.example.forgotpasswordservice.repository.UserRepository;
import com.example.forgotpasswordservice.service.ForgotPasswordService;
import com.ldubgd.components.dao.*;
import com.ldubgd.components.dao.enums.StatementStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;
    private final ForgotPasswordInfoRepository forgotPasswordInfoRepository;

    @Override
    public void createForgotPasswordStatement(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        User user = userRepository.findById(forgotPasswordRequestDto.getUserId()).orElseThrow(
                () -> new RecourseNotFoundException("User not found")
        );
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setTypeOfForgotPassword(forgotPasswordRequestDto.getTypeOfForgotPassword());
        forgotPassword.setUser(user);

        forgotPasswordRepository.save(forgotPassword);

        ForgotPasswordInfo forgotPasswordInfo = new ForgotPasswordInfo();
        forgotPasswordInfo.setForgotPassword(forgotPassword);
        forgotPasswordInfo.setIsReady(false);
        forgotPasswordInfo.setStatementStatus(StatementStatus.PENDING);

        forgotPasswordInfoRepository.save(forgotPasswordInfo);
    }
}
