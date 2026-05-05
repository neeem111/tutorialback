package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.cliente.ClienteService;
import com.ccsw.tutorial.game.GameService;
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
    ClienteService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<prestamo> find(prestamoSearchDto dto) {

        Specification<prestamo> spec = prestamoSpecification.createSpecification(dto);

        return this.prestamoRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, prestamoDto dto) {

        prestamo prestamo;

        if (id == null) {
            prestamo = new prestamo();
        } else {
            prestamo = this.prestamoRepository.findById(id).orElse(null);
        }

        if (dto.getEndDate().before(dto.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la de inicio");
        }

        long diff = dto.getEndDate().getTime() - dto.getStartDate().getTime();
        long days = diff / (1000 * 60 * 60 * 24);

        if (days > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo no puede superar 14 días");
        }

        List<prestamo> overlappingGame = this.prestamoRepository.findOverlappingGame(dto.getGame().getId(), id, dto.getStartDate(), dto.getEndDate());

        if (!overlappingGame.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado en ese rango de fechas");
        }

        List<prestamo> overlappingClient = this.prestamoRepository.findOverlappingClient(dto.getClient().getId(), id, dto.getStartDate(), dto.getEndDate());

        if (overlappingClient.size() >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya tiene 2 préstamos en ese rango de fechas");
        }

        prestamo.setGame(gameService.get(dto.getGame().getId()));
        prestamo.setClient(clientService.get(dto.getClient().getId()));
        prestamo.setStartDate(dto.getStartDate());
        prestamo.setEndDate(dto.getEndDate());

        this.prestamoRepository.save(prestamo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {

        this.prestamoRepository.deleteById(id);
    }
}