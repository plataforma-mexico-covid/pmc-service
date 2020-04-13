package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.AyudaDTO;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class AyudaMapper {

    public static AyudaDTO from(final Ayuda ayuda) {
        return from(ayuda, null);
    }

    public static AyudaDTO from(final Ayuda ayuda, final String usernane) {
        final AyudaDTO ayudaDTO = new AyudaDTO();
        ayudaDTO.setId(ayuda.getId());
        ayudaDTO.setDescripcion(ayuda.getDescripcion());
        ayudaDTO.setCiudadano(CiudadanoMapper.from(ayuda.getCiudadano()));
        ayudaDTO.setUbicacion(AddressMapper.from(ayuda.getUbicacion()));
        ayudaDTO.setTipoAyuda(TipoAyudaMapper.from(ayuda.getTipoAyuda()));
        ayudaDTO.setOrigenAyuda(ayuda.getOrigenAyuda());
        ayudaDTO.setFechaRegistro(DateUtil.formatDTO(ayuda.getFechaRegistro()));
        ayudaDTO.setIsUserLogIn(false);
        if (usernane != null && ayuda.getCiudadano().getUser().getUsername() != null) {
            ayudaDTO.setIsUserLogIn(usernane.equals(ayuda.getCiudadano().getUser().getUsername()));
        }
        return ayudaDTO;
    }

    public static Ayuda from(final AyudaDTO ayudaDTO) {
        final Ayuda ayuda = new Ayuda();
        ayuda.setDescripcion(ayudaDTO.getDescripcion());
        ayuda.setCiudadano(CiudadanoMapper.from(ayudaDTO.getCiudadano()));
        ayuda.setUbicacion(AddressMapper.from(ayudaDTO.getUbicacion()));
        ayuda.setTipoAyuda(TipoAyudaMapper.from(ayudaDTO.getTipoAyuda()));
        ayuda.setOrigenAyuda(ayudaDTO.getOrigenAyuda());
        ayuda.setFechaRegistro(DateUtil.parseDTO(ayudaDTO.getFechaRegistro()));
        return ayuda;
    }

    public static List<AyudaDTO> fromAndMarkByUser(final List<Ayuda> ayudas, final String usernane) {
        return ayudas.stream().map(it -> AyudaMapper.from(it, usernane)).collect(Collectors.toList());
    }
}
