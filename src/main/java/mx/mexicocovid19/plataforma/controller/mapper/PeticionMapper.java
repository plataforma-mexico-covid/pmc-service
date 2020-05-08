package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.PeticionDTO;
import mx.mexicocovid19.plataforma.model.entity.Peticion;
import mx.mexicocovid19.plataforma.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PeticionMapper {
    public static PeticionDTO from(Peticion peticion) {
        final PeticionDTO dto = new PeticionDTO();
        dto.setId(peticion.getId());
        dto.setAyuda(AyudaMapper.from(peticion.getAyuda()));
        dto.setTipoMatch(peticion.getTipoMatch());
        dto.setFechaPeticion(DateUtil.formatDTO(peticion.getFechaPeticion()));
        if (peticion.getAyudaMatch() != null){
            dto.setAyudaMatch(AyudaMapper.from(peticion.getAyudaMatch()));
        }
        if (peticion.getCiudadano() != null){
            dto.setCiudadano(CiudadanoMapper.from(peticion.getCiudadano()));
        }
        return dto;
    }

    public static List<PeticionDTO> from(final List<Peticion> peticiones) {
        return peticiones.stream().map(PeticionMapper::from).collect(Collectors.toList());
    }
}
