package com.example.forgotpasswordservice.mapper;

import com.example.forgotpasswordservice.dto.ForgotPasswordDto;
import com.ldubgd.components.dao.enums.StatementStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForgotPasswordMapper {
    public static ForgotPasswordDto mapToForgotPasswordDto(Object[] result) {
        ForgotPasswordDto dto = new ForgotPasswordDto();

        dto.setId((Long) result[0]);
        dto.setTypeOfForgotPassword((String) result[1]);
        dto.setUserId((Long) result[2]);

        Object statusObject = result[3];
        String statusString;
        if (statusObject instanceof Boolean) {
            statusString = (Boolean) statusObject ? "READY" : "PENDING";
        } else {
            statusString = (String) statusObject;
        }
        StatementStatus status = StatementStatus.valueOf(statusString);
        switch (status) {
            case IN_PROGRESS:
                dto.setStatus("В процесі");
                break;
            case READY:
                dto.setStatus("Готово");
                break;
            case PENDING:
                dto.setStatus("В очікуванні");
                break;
            default:
                dto.setStatus("Невідомий статус");
                break;
        }

        dto.setFullName((String) result[4]);
        dto.setGroupName((String) result[5]);
        dto.setFaculty((String) result[6]);

        return dto;
    }
}
