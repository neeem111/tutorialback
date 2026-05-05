package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author ccsw
 *
 */
public interface LoanRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Loan> {

    /**
     * Método para buscar préstamos de un juego que se solapen en un rango de fechas
     *
     * @param gameId id del juego
     * @param loanId id del préstamo
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return lista de préstamos solapados
     */
    @Query("""
                SELECT l FROM Prestamo l
                WHERE l.game.id = :gameId
                AND l.id <> COALESCE(:prestamoId, -1)
                AND l.startDate <= :endDate 
                AND l.endDate >= :startDate
            """)
    List<Prestamo> findOverlappingGame(Long gameId, Long loanId, Date startDate, Date endDate);

    /**
     * Método para buscar préstamos de un cliente que se solapen en un rango de fechas
     *
     * @param clientId id del cliente
     * @param loanId id del préstamo
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return lista de préstamos solapados
     */
    @Query("""
                SELECT l FROM Loan l
                WHERE l.client.id = :clientId
                AND l.id <> COALESCE(:loanId, -1)
                AND l.startDate <= :endDate 
                AND l.endDate >= :startDate
            """)
    List<Loan> findOverlappingClient(Long clientId, Long loanId, Date startDate, Date endDate);
}