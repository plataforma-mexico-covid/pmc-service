package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.InfoSensibleDTO;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.model.entity.Peticion;

import java.util.List;

public class InfoSensibleMapper {
    public static InfoSensibleDTO from(final Ciudadano ciudadano, final List<Peticion> matchs) {
        InfoSensibleDTO dto = new InfoSensibleDTO();
        dto.setUsername(ciudadano.getUser().getUsername());
        dto.setContactos(CiudadanoContactoMapper.from(ciudadano.getContactos()));
        dto.setMatchs(PeticionMapper.from(matchs));
        return dto;
    }
}
