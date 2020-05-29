package mx.mexicocovid19.plataforma.model.repository;

import mx.mexicocovid19.plataforma.model.entity.Oferta;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.model.entity.EstatusAyuda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
    @Query("select oferta " +
            " FROM Oferta oferta" +
            " where " +
            "   oferta.estatusOferta in ('NUEVA', 'EN_PROGRESO') " +
            "   and ( 6371 * " +
            "      acos ( " +
            "         cos ( radians ( :latitudeRef ) ) " +
            "         * cos ( radians ( oferta.ubicacion.latitude ) ) " +
            "         * cos ( radians ( oferta.ubicacion.longitude ) - radians ( :longitudeRef ) ) " +
            "         + sin ( radians ( :latitudeRef ) ) " +
            "         * sin ( radians ( oferta.ubicacion.latitude ) ) " +
            "      ) " +
            "   ) " +
            "     <= :kilometers ")
    List<Oferta> findByAllInsideOfKilometers(@Param("latitudeRef") double latitudeRef,
                                           @Param("longitudeRef") double longitudeRef,
                                           @Param("kilometers") double kilometers);
    
    @Query("select oferta from Oferta oferta " +
            "where oferta.estatusOferta = :estatusOferta ")
    List<Oferta> findByEstatusOferta(@Param("estatusOferta") EstatusAyuda estatusOferta);


    @Query("select oferta from Oferta oferta " +
            "where " +
            "   (oferta.estatusOferta = :estatusOferta or null = :estatusOferta) and " +
            "   (oferta.nombre LIKE %:search% or " +
            "    oferta.descripcion LIKE %:search% or " +
            "    oferta.ciudadano.nombre LIKE %:search% or " +
            "    oferta.ciudadano.paterno LIKE %:search% or " +
            "    oferta.ciudadano.materno LIKE %:search% or " +
            "    oferta.ubicacion.codigoPostal LIKE %:search% or " +
            "    oferta.ciudadano.user.username LIKE %:search%)")
    Page<Oferta> findByFilter(@Param("estatusOferta") EstatusAyuda estatusOferta,
                              @Param("search") String search,
                              Pageable pageable);

    List<Oferta> findByCiudadano(Ciudadano ciudadano);
}
