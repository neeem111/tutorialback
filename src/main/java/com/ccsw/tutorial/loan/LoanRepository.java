package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    @Query("""
                SELECT l FROM Loan l
                WHERE (:clientId IS NULL OR l.cliente.id = :clientId)
                AND (:gameId IS NULL OR l.game.id = :gameId)
                AND (:date IS NULL OR :date BETWEEN l.startDate AND l.endDate)
            """)
    Page<Loan> findLoans(@Param("clientId") Long clientId, @Param("gameId") Long gameId, @Param("date") LocalDate date, Pageable pageable);

    @Query("""
                SELECT l FROM Loan l
                WHERE l.game.id = :gameId
                AND :date BETWEEN l.startDate AND l.endDate
                AND (:excludeId IS NULL OR l.id <> :excludeId)
            """)
    List<Loan> findGameLoansByDate(@Param("gameId") Long gameId, @Param("date") LocalDate date, @Param("excludeId") Long excludeId);

    @Query("""
                SELECT COUNT(l) FROM Loan l
                WHERE l.cliente.id = :clientId
                AND :date BETWEEN l.startDate AND l.endDate
                AND (:excludeId IS NULL OR l.id <> :excludeId)
            """)
    long countClientLoansByDate(@Param("clientId") Long clientId, @Param("date") LocalDate date, @Param("excludeId") Long excludeId);
}