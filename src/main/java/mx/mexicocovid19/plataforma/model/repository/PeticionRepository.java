package mx.mexicocovid19.plataforma.model.repository;

import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.model.entity.Peticion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeticionRepository extends JpaRepository<Peticion, Integer> {
    List<Peticion> findAllByAyuda(Ayuda ayuda);
}
