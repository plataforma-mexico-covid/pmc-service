package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapPositionsDTO {
    List<AyudaDTO> ayudas;
    List<OfertaDTO> ofertas;
}
