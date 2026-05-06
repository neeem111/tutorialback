package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.cliente.ClienteService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClienteService clienteService;

    @Override
    public Page<Prestamo> find(PrestamoSearchDto dto) {
        Specification<Prestamo> spec = PrestamoSpecification.createSpecification(dto);
        return this.prestamoRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, PrestamoDto dto) {
        Prestamo prestamo;

        if (id == null) {
            prestamo = new Prestamo();
        } else {
            prestamo = this.prestamoRepository.findById(id).orElse(null);
        }

        if (dto.getEndDate().before(dto.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha fin no puede ser anterior a la de inicio");
        }

        long diff = dto.getEndDate().getTime() - dto.getStartDate().getTime();
        long days = diff / (1000L * 60 * 60 * 24);
        if (days > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El préstamo no puede superar 14 días");
        }

        List<Prestamo> overlappingGame = this.prestamoRepository.findOverlappingGame(
                dto.getGame().getId(), id, dto.getStartDate(), dto.getEndDate());
        if (!overlappingGame.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El juego ya está prestado en ese rango de fechas");
        }

        List<Prestamo> overlappingClient = this.prestamoRepository.findOverlappingClient(
                dto.getClient().getId(), id, dto.getStartDate(), dto.getEndDate());
        if (overlappingClient.size() >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El cliente ya tiene 2 préstamos en ese rango de fechas");
        }

        prestamo.setGame(gameService.get(dto.getGame().getId()));
        prestamo.setClient(clienteService.get(dto.getClient().getId()));
        prestamo.setStartDate(dto.getStartDate());
        prestamo.setEndDate(dto.getEndDate());

        this.prestamoRepository.save(prestamo);
    }

    @Override
    public void delete(Long id) {
        this.prestamoRepository.deleteById(id);
    }
}
