package mx.mexicocovid19.plataforma.model.repository;

import mx.mexicocovid19.plataforma.model.entity.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer> {
}
