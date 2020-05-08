package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfoSensibleDTO {
    private String username;
    private List<CiudadanoContactoDTO> contactos;
    private List<PeticionDTO> matchs;
}
