package domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByTitulo(String titulo);

    @Query("SELECT t FROM Topico t ORDER BY t.fecha_creacion ASC")
    Page<Topico> listadoTopico(Pageable paginacion);

    @Modifying
    @Query("DELETE FROM Topico t WHERE t.id = :idTopico")
    int borrarTopico(Long idTopico);
}
