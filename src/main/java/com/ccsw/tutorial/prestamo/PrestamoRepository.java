package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {

    Page<Prestamo> findAll(Specification<Prestamo> spec, Pageable pageable);

    @Query("""
            SELECT p FROM Prestamo p
            WHERE p.game.id = :gameId
            AND p.id <> COALESCE(:prestamoId, -1)
            AND p.startDate <= :endDate
            AND p.endDate >= :startDate
            """)
    List<Prestamo> findOverlappingGame(
            @Param("gameId") Long gameId,
            @Param("prestamoId") Long prestamoId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("""
            SELECT p FROM Prestamo p
            WHERE p.client.id = :clientId
            AND p.id <> COALESCE(:prestamoId, -1)
            AND p.startDate <= :endDate
            AND p.endDate >= :startDate
            """)
    List<Prestamo> findOverlappingClient(
            @Param("clientId") Long clientId,
            @Param("prestamoId") Long prestamoId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
