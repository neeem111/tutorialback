package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface LoanService {

    Page<LoanDto> findPage(Long clientId, Long gameId, LocalDate date, int page, int size);

    void save(Long id, LoanDto dto) throws Exception;

    void delete(Long id) throws Exception;
}