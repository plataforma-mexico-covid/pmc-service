package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoluntarioDTO {
    private Integer id;
    private CiudadanoDTO ciudadano;
    private DisciplinaDTO disciplina;
    private AddressDTO ubicacion;
    private String descripcion;
    private String username;
    private Boolean active;
}
