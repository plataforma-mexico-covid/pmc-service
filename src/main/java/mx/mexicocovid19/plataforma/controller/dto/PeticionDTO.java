package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;
import mx.mexicocovid19.plataforma.model.entity.TipoMatch;

@Getter
@Setter
public class PeticionDTO {
    private int id;
    private AyudaDTO ayuda;
    private CiudadanoDTO ciudadano;
    private AyudaDTO ayudaMatch;
    private TipoMatch tipoMatch;
    private String fechaPeticion;
}
