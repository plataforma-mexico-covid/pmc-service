package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;
import mx.mexicocovid19.plataforma.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.model.entity.RangoPrecio;

@Getter
@Setter
public class OfertaDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String descCorta;
    private CiudadanoDTO ciudadano;
    private AddressDTO ubicacion;
    private RangoPrecio rangoPrecio;
    private String fechaRegistro;
    private EstatusAyuda estatusOferta;
    private Boolean isUserLogIn;
}
