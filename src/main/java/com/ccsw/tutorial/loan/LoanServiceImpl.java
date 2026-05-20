package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.cliente.ClienteService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private static final int MAX_DAYS = 14;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClienteService clientService;

    @Autowired
    private GameService gameService;

    /*
        @Override
        public Page<LoanDto> findPage(Long clientId, Long gameId, LocalDate date, int page, int size) {

            return this.loanRepository.findLoans(clientId, gameId, date, PageRequest.of(page, size)).map(loan -> {
                LoanDto dto = new LoanDto();
                dto.setId(loan.getId());
                dto.setClientId(loan.getClient().getId());
                dto.setClientName(loan.getClient().getName());
                dto.setGameId(loan.getGame().getId());
                dto.setGameTitle(loan.getGame().getTitle());
                dto.setStartDate(loan.getStartDate());
                dto.setEndDate(loan.getEndDate());
                return dto;
            });
        }
    */
    @Override
    public Page<LoanDto> findPage(Long clientId, Long gameId, LocalDate date, int page, int size) {

        Specification<Loan> spec = Specification.allOf(LoanSpecification.hasClient(clientId), LoanSpecification.hasGame(gameId), LoanSpecification.hasDate(date));

        return this.loanRepository.findAll(spec, PageRequest.of(page, size)).map(loan -> {
            LoanDto dto = modelMapper.map(loan, LoanDto.class);
            //para no cambias todo el mapper
            dto.setClientId(loan.getClient().getId());
            dto.setClientName(loan.getClient().getName());
            dto.setGameId(loan.getGame().getId());
            dto.setGameTitle(loan.getGame().getTitle());

            return dto;
        });
    }

    @Override
    public void save(Long id, LoanDto dto) throws Exception {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la fecha inicio");
        }

        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        if (days > MAX_DAYS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El prestamo no puede superar los 14 días");
        }

        Loan loan = (id == null) ? new Loan() : loanRepository.findById(id).orElseThrow();

        loan.setClient(clientService.get(dto.getClientId()));
        loan.setGame(gameService.get(dto.getGameId()));
        loan.setStartDate(dto.getStartDate());
        loan.setEndDate(dto.getEndDate());

        for (LocalDate d = dto.getStartDate(); !d.isAfter(dto.getEndDate()); d = d.plusDays(1)) {

            LocalDate currentDate = d;
            Specification<Loan> gameSpec = (root, query, builder) -> {

                var predicate = builder.and(builder.equal(root.get("game").get("id"), dto.getGameId()), builder.between(builder.literal(currentDate), root.get("startDate"), root.get("endDate")));

                if (id != null) {
                    predicate = builder.and(predicate, builder.notEqual(root.get("id"), id));
                }

                return predicate;
            };

            if (!loanRepository.findAll(gameSpec).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado en la fecha " + currentDate);
            }

            Specification<Loan> clientSpec = (root, query, builder) -> {

                var predicate = builder.and(builder.equal(root.get("cliente").get("id"), dto.getClientId()), builder.between(builder.literal(currentDate), root.get("startDate"), root.get("endDate")));

                if (id != null) {
                    predicate = builder.and(predicate, builder.notEqual(root.get("id"), id));
                }

                return predicate;
            };

            long clientLoans = loanRepository.count(clientSpec);

            if (clientLoans >= 2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya tiene más de 2 prestamos en la fecha " + currentDate);
            }
        }

        loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!loanRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el prestamo");
        }
        loanRepository.deleteById(id);
    }
}