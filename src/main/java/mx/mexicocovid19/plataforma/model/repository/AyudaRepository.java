package mx.mexicocovid19.plataforma.model.repository;

import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.model.entity.OrigenAyuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AyudaRepository extends JpaRepository<Ayuda, Integer> {
    @Query("select ayuda " +
            " FROM Ayuda ayuda" +
            " where " +
            "   ayuda.estatusAyuda in ('NUEVA', 'EN_PROGRESO') " +
            "   and ( 6371 * " +
            "      acos ( " +
            "         cos ( radians ( :latitudeRef ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.latitude ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.longitude ) - radians ( :longitudeRef ) ) " +
            "         + sin ( radians ( :latitudeRef ) ) " +
            "         * sin ( radians ( ayuda.ubicacion.latitude ) ) " +
            "      ) " +
            "   ) " +
            "     <= :kilometers ")
    List<Ayuda> findByAllInsideOfKilometers(@Param("latitudeRef") double latitudeRef,
                                           @Param("longitudeRef") double longitudeRef,
                                           @Param("kilometers") double kilometers);
    @Query("select ayuda " +
            " FROM Ayuda ayuda" +
            " where " +
            "   ayuda.origenAyuda = :origenAyuda " +
            "   and ayuda.estatusAyuda in ('NUEVA', 'EN_PROGRESO') " +
            "   and ( 6371 * " +
            "      acos ( " +
            "         cos ( radians ( :latitudeRef ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.latitude ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.longitude ) - radians ( :longitudeRef ) ) " +
            "         + sin ( radians ( :latitudeRef ) ) " +
            "         * sin ( radians ( ayuda.ubicacion.latitude ) ) " +
            "      ) " +
            "   ) " +
            "     <= :kilometers ")
    List<Ayuda> findByAllInsideOfKilometersByOrigenAyuda(@Param("latitudeRef") double latitudeRef,
                                                         @Param("longitudeRef") double longitudeRef,
                                                         @Param("kilometers") double kilometers,
                                                         @Param("origenAyuda")OrigenAyuda origenAyuda);
}
