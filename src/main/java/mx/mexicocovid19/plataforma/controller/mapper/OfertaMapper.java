package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.OfertaDTO;
import mx.mexicocovid19.plataforma.model.entity.Oferta;
import mx.mexicocovid19.plataforma.util.DateUtil;
import mx.mexicocovid19.plataforma.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class OfertaMapper {

    public static OfertaDTO from(final Oferta oferta) {
        return from(oferta, null);
    }

    public static OfertaDTO from(final Oferta oferta, final String usernane) {
        final OfertaDTO ofertaDTO = new OfertaDTO();
        ofertaDTO.setId(oferta.getId());
        ofertaDTO.setNombre(oferta.getNombre());
        ofertaDTO.setDescripcion(oferta.getDescripcion());
        ofertaDTO.setDescCorta(Utils.truncateDescription(oferta.getDescripcion(), 50));
        ofertaDTO.setCiudadano(CiudadanoMapper.from(oferta.getCiudadano()));
        ofertaDTO.setUbicacion(AddressMapper.from(oferta.getUbicacion()));
        ofertaDTO.setRangoPrecio(oferta.getRangoPrecio());
        ofertaDTO.setFechaRegistro(DateUtil.formatDTO(oferta.getFechaRegistro()));
        ofertaDTO.setEstatusOferta(oferta.getEstatusOferta());
        ofertaDTO.setIsUserLogIn(false);
        if (usernane != null && oferta.getCiudadano().getUser() != null && oferta.getCiudadano().getUser().getUsername() != null) {
            ofertaDTO.setIsUserLogIn(usernane.equals(oferta.getCiudadano().getUser().getUsername()));
        }
        return ofertaDTO;
    }

    public static Oferta from(final OfertaDTO ofertaDTO) {
        final Oferta oferta = new Oferta();
        oferta.setDescripcion(Utils.truncateDescription(ofertaDTO.getDescripcion(), 1000));
        oferta.setCiudadano(CiudadanoMapper.from(ofertaDTO.getCiudadano()));
        oferta.setUbicacion(AddressMapper.from(ofertaDTO.getUbicacion()));
        oferta.setRangoPrecio(ofertaDTO.getRangoPrecio());
        oferta.setEstatusOferta(ofertaDTO.getEstatusOferta());
        oferta.setFechaRegistro(DateUtil.parseDTO(ofertaDTO.getFechaRegistro()));
        return oferta;
    }

    public static Oferta fromSimple(final OfertaDTO ofertaDTO) {
        final Oferta oferta = new Oferta();
        oferta.setId(ofertaDTO.getId());
        oferta.setNombre(Utils.truncateDescription(ofertaDTO.getNombre(), 200));
        oferta.setDescripcion(Utils.truncateDescription(ofertaDTO.getDescripcion(), 1000));
        oferta.setRangoPrecio(ofertaDTO.getRangoPrecio());
        return oferta;
    }

    public static List<OfertaDTO> fromAndMarkByUser(final List<Oferta> ofertas, final String usernane) {
        return ofertas.stream().map(it -> OfertaMapper.from(it, usernane)).collect(Collectors.toList());
    }
    
    public static List<OfertaDTO> from(final List<Oferta> ofertas) {
        return ofertas.stream().map(it -> OfertaMapper.from(it)).collect(Collectors.toList());
    }
}
