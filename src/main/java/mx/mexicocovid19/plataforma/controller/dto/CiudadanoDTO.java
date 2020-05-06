package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CiudadanoDTO {
    private Integer id;
    private String nombre;
    private String paterno;
    private String materno;
    private String nombreCompleto;
    private String username;
    private List<CiudadanoContactoDTO> contactos;
}
