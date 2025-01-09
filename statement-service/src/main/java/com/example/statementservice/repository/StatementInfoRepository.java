package com.example.statementservice.repository;

import com.example.statementservice.entity.StatementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StatementInfoRepository extends JpaRepository<StatementInfo, Long> {
    @Query(value = "SELECT s.id, s.full_name, s.group_name, s.phone_number, s.type_of_statement, s.faculty, s.year_birthday, si.is_ready " +
            "FROM statement_info si " +
            "JOIN statement s ON si.id = s.id " +
            "WHERE si.statement_status = 'PENDING'",
            nativeQuery = true)
    List<Object[]> findStatementsInfoWithStatusPending();

    /**
     * Retrieves statement information with a status of false, filtered by faculty.
     *
     * @param faculty the faculty to filter by
     * @return a list of statement information with a status of false and the given faculty
     */
    @Query(value = "SELECT s.id, s.full_name, s.group_name, s.phone_number, s.type_of_statement, s.faculty, s.year_birthday, si.statement_status " +
            "FROM statement_info si " +
            "JOIN statement s ON si.id = s.id " +
            "WHERE s.faculty = :faculty",
            nativeQuery = true)
    List<Object[]> findStatementInfoByFaculty(@Param("faculty") String faculty);

    @Query(value = "SELECT s.id, s.full_name, s.group_name, s.phone_number, s.type_of_statement, s.faculty, s.year_birthday, si.statement_status " +
            "FROM statement_info si " +
            "JOIN statement s ON si.id = s.id " +
            "WHERE si.statement_status = :status",
            nativeQuery = true)
    List<Object[]> findStatementInfoByStatus(@Param("status") String status);

    @Query(value = "SELECT s.id, s.full_name, s.group_name, s.phone_number, s.type_of_statement, s.faculty, s.year_birthday, si.statement_status " +
            "FROM statement_info si " +
            "JOIN statement s ON si.id = s.id " +
            "WHERE si.statement_status = :status AND s.faculty = :faculty",
            nativeQuery = true)
    List<Object[]> findStatementInfoByStatusAndFaculty(@Param("status") String status, @Param("faculty") String faculty);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM statement_info si " +
            "USING statement s " +
            "WHERE si.statement_status = :status " +
            "AND si.id = :statementId " +
            "AND s.faculty = :faculty " +
            "AND si.id = s.id",
            nativeQuery = true)
    void deleteStatementIfReady(@Param("status") String status,
                                @Param("statementId") Long statementId,
                                @Param("faculty") String faculty);

    @Query(value = "SELECT s.id, s.full_name, s.group_name, s.phone_number, s.type_of_statement, s.faculty, s.year_birthday, si.statement_status " +
            "FROM statement_info si " +
            "JOIN statement s ON si.id = s.id " +
            "WHERE LOWER(s.full_name) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true)
    List<Object[]> findByNameContaining(@Param("name") String name);

}
