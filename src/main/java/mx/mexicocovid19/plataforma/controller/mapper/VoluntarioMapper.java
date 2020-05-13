package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.VoluntarioDTO;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.model.entity.Voluntario;
import mx.mexicocovid19.plataforma.util.Utils;

public class VoluntarioMapper {

    public static VoluntarioDTO from(final Voluntario voluntario) {
        final VoluntarioDTO voluntarioDTO = new VoluntarioDTO();
        voluntarioDTO.setId(voluntario.getId());
        voluntarioDTO.setCiudadano(CiudadanoMapper.from(voluntario.getCiudadano()));
        voluntarioDTO.setUbicacion(AddressMapper.from(voluntario.getUbicacion()));
        voluntarioDTO.setDisciplina(DisciplinaMapper.from(voluntario.getDisciplina()));
        voluntarioDTO.setDescripcion(voluntario.getDescripcion());
        voluntarioDTO.setActive(voluntario.getActive());

        return voluntarioDTO;
    }

    public static Voluntario from(final VoluntarioDTO voluntarioDTO) {
        final Voluntario voluntario = new Voluntario();
        User user = new User();
        user.setUsername(voluntarioDTO.getUsername());
        voluntario.setCiudadano(CiudadanoMapper.from(voluntarioDTO.getCiudadano()));
        voluntario.getCiudadano().setUser(user);
        voluntario.setUbicacion(AddressMapper.from(voluntarioDTO.getUbicacion()));
        voluntario.setDisciplina(DisciplinaMapper.from(voluntarioDTO.getDisciplina()));
        voluntario.setDescripcion(Utils.truncateDescription(voluntarioDTO.getDescripcion(), 1000));
        return voluntario;
    }
}
