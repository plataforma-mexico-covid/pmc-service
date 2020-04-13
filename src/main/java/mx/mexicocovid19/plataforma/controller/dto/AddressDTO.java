package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private int id;
    private String calle;
    private String direccion;
    private String noExterior;
    private String noInterior;
    private String colonia;
    private String codigoPostal;
    private int idMunicipio;
    private String municipio;
    private int idEstado;
    private String estado;
    private Double longitude;
    private Double latitude;
}
