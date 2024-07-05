package domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    @Query("SELECT r FROM Respuesta r ORDER BY r.fecha_creacion ASC")
    Page<Respuesta> listarRespuestas(Pageable paginacion);

    @Modifying
    @Transactional
    @Query("DELETE FROM Respuesta r WHERE r.id = :idRespuesta")
    void borrarRespuesta(Long idRespuesta);
}

