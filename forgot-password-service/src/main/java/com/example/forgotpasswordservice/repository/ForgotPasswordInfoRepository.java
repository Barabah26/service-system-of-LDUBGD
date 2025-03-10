package com.example.forgotpasswordservice.repository;

import com.ldubgd.components.dao.ForgotPasswordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForgotPasswordInfoRepository extends JpaRepository<ForgotPasswordInfo, Long> {
    @Query(value = "SELECT fp.id, fp.type_of_forgot_password, fp.user_id, fpi.statement_status, u.name, u.student_group, u.faculty " +
            "FROM forgot_password_info fpi " +
            "JOIN forgot_password fp ON fpi.id = fp.id " +
            "JOIN users u ON fp.user_id = u.user_id " +
            "WHERE fpi.statement_status = :status",
            nativeQuery = true)
    List<Object[]> findForgotPasswordInfoByStatus(@Param("status") String status);

    @Query(value = "SELECT fp.id, fp.type_of_forgot_password, fp.user_id, fpi.statement_status, u.name, u.student_group, u.faculty " +
            "FROM forgot_password_info fpi " +
            "JOIN forgot_password fp ON fpi.id = fp.id " +
            "JOIN users u ON fp.user_id = u.user_id " +
            "WHERE fpi.statement_status = :status AND u.faculty = :faculty",
            nativeQuery = true)
    List<Object[]> findForgotPasswordByStatusAndFaculty(@Param("status") String status, @Param("faculty") String faculty);

    @Query(value = "SELECT fp.id, fp.type_of_forgot_password, fp.user_id, fpi.statement_status, u.name, u.student_group, u.faculty " +
            "FROM forgot_password_info fpi " +
            "JOIN forgot_password fp ON fpi.id = fp.id " +
            "JOIN users u ON fp.user_id = u.user_id " +
            "WHERE u.user_id = :id",
            nativeQuery = true)
    List<Object[]> findForgotPasswordInfoByUserId(@Param("id") Long userId);
}

