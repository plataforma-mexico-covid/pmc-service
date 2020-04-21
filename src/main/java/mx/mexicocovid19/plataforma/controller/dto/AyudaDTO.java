package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;
import mx.mexicocovid19.plataforma.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.model.entity.OrigenAyuda;

@Getter
@Setter
public class AyudaDTO {
    private Integer id;
    private String descripcion;
    private CiudadanoDTO ciudadano;
    private AddressDTO ubicacion;
    private TipoAyudaDTO tipoAyuda;
    private OrigenAyuda origenAyuda;
    private String fechaRegistro;
    private String campana;
    private String medio;
    private String origen;
    private EstatusAyuda estatusAyuda;
    private Boolean isUserLogIn;
}
