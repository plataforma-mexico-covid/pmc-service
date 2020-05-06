package mx.mexicocovid19.plataforma.model.repository;

import mx.mexicocovid19.plataforma.model.entity.CiudadanoContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadanoContactoRepository extends JpaRepository<CiudadanoContacto, Integer> {
    List<CiudadanoContacto> findAllByContacto(final String contacto);
}
