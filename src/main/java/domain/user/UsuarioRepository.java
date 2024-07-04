package domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long aLong);

    UserDetails findByEmail(String username);

    @Query("SELECT u FROM Usuario u ORDER BY u.id ASC")
    Page<Usuario> listarUsuarios(Pageable paginacion);


}
