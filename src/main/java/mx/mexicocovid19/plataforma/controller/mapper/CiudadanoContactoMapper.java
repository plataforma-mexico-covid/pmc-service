package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.CiudadanoContactoDTO;
import mx.mexicocovid19.plataforma.model.entity.CiudadanoContacto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CiudadanoContactoMapper {
    public static CiudadanoContactoDTO from(final CiudadanoContacto ciudadanoContacto) {
        final CiudadanoContactoDTO ciudadanoContactoDTO = new CiudadanoContactoDTO();
        ciudadanoContactoDTO.setId(ciudadanoContacto.getId());
        ciudadanoContactoDTO.setTipoContacto(ciudadanoContacto.getTipoContacto());
        ciudadanoContactoDTO.setContacto(ciudadanoContacto.getContacto());
        return ciudadanoContactoDTO;
    }

    public static CiudadanoContacto from(final CiudadanoContactoDTO ciudadanoContactoDTO) {
        final CiudadanoContacto ciudadanoContacto = new CiudadanoContacto();
        ciudadanoContacto.setId(ciudadanoContactoDTO.getId());
        ciudadanoContacto.setTipoContacto(ciudadanoContactoDTO.getTipoContacto());
        ciudadanoContacto.setContacto(ciudadanoContactoDTO.getContacto());
        return ciudadanoContacto;
    }

    public static Set<CiudadanoContacto> from(final List<CiudadanoContactoDTO> ciudadanoContactosDTO) {
        return ciudadanoContactosDTO.stream().map(CiudadanoContactoMapper::from).collect(Collectors.toSet());
    }

    public static List<CiudadanoContactoDTO> from(final Set<CiudadanoContacto> ciudadanoContactos) {
        return ciudadanoContactos.stream().map(CiudadanoContactoMapper::from).collect(Collectors.toList());
    }
}
