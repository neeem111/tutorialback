package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.LoanDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Loan", description = "API of Loan")
@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Operation(summary = "Find Loans")
    @GetMapping
    public Page<LoanDto> findPage(@RequestParam(required = false) Long clientId, @RequestParam(required = false) Long gameId, @RequestParam(required = false) LocalDate date, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return loanService.findPage(clientId, gameId, date, page, size);
    }

    @Operation(summary = "Save or Update Loan")
    @PutMapping({ "", "/{id}" })
    public void save(@PathVariable(required = false) Long id, @RequestBody LoanDto dto) throws Exception {

        loanService.save(id, dto);
    }

    @Operation(summary = "Delete Loan")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {

        loanService.delete(id);
    }
}