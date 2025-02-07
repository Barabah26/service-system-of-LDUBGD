package com.example.forgotpasswordservice.service.impl;

import com.example.forgotpasswordservice.dto.ForgotPasswordDto;
import com.example.forgotpasswordservice.dto.ForgotPasswordRequestDto;
import com.example.forgotpasswordservice.exception.RecourseNotFoundException;
import com.example.forgotpasswordservice.mapper.ForgotPasswordMapper;
import com.example.forgotpasswordservice.repository.ForgotPasswordInfoRepository;
import com.example.forgotpasswordservice.repository.ForgotPasswordRepository;
import com.example.forgotpasswordservice.repository.UserRepository;
import com.example.forgotpasswordservice.service.ForgotPasswordService;
import com.ldubgd.components.dao.*;
import com.ldubgd.components.dao.enums.StatementStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ForgotPasswordDto> getForgotPasswordInfoByStatus(StatementStatus status) {
        List<Object[]> results = forgotPasswordInfoRepository.findForgotPasswordInfoByStatus(status.name());
        return results.stream().map(ForgotPasswordMapper::mapToForgotPasswordDto).collect(Collectors.toList());
    }

    @Override
    public void updateForgotPasswordStatus(Long forgotPasswordId, StatementStatus status) {
        ForgotPasswordInfo forgotPasswordInfo = forgotPasswordInfoRepository.findById(forgotPasswordId).orElseThrow(
                () -> new RecourseNotFoundException("ForgotPassword is not found with id: " + forgotPasswordId)
        );

        forgotPasswordInfo.setStatementStatus(StatementStatus.valueOf(status.name()));
        forgotPasswordInfoRepository.save(forgotPasswordInfo);
    }

    @Override
    public boolean updateForgotPassword(Long id, String login, String password) {
        int updatedRows = forgotPasswordRepository.updateIfNull(id, login, password);
        return updatedRows > 0;
    }

    @Override
    public List<ForgotPasswordDto> getForgotPasswordByStatusAndFaculty(StatementStatus status, String faculty) {
        List<Object[]> results = forgotPasswordInfoRepository.findForgotPasswordByStatusAndFaculty(status.name(), faculty);
        return results.stream().map(ForgotPasswordMapper::mapToForgotPasswordDto).collect(Collectors.toList());
    }

    @Override
    public List<ForgotPasswordDto> findForgotPasswordInfoByUserId(Long userId) {
        List<Object[]> statements = forgotPasswordInfoRepository.findForgotPasswordInfoByUserId(userId);
        if (statements.isEmpty()) {
            return Collections.emptyList();
        }
        return statements.stream().map(ForgotPasswordMapper::mapToForgotPasswordDto).collect(Collectors.toList());
    }
}
