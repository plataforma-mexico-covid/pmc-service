package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackDTO {
	
	 private Integer id;
	 private CiudadanoDTO ciudadano;
	 private String descripcion;
	 private String fechaCreacion;

}
