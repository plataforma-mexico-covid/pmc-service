package mx.mexicocovid19.plataforma.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackDTO {
	
	 private Integer id;
	 private Integer ciudadano_id;
	 private Integer peticion_id;
	 private String mensaje;
	 private String fechaCreacion;

}
