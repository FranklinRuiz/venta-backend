package pe.utp.venta.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.utp.venta.persistence.entity.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
   @EntityGraph(attributePaths = "authorities")
   Optional<Usuario> findOneWithAuthoritiesByUsername(String username);
}
