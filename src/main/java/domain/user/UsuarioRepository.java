package domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends JpaRepository<Usuario, Long> {



}
