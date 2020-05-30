package mx.mexicocovid19.plataforma.service;

import mx.mexicocovid19.plataforma.controller.dto.OfertaDTO;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageRequest;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageResponse;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Oferta;
import mx.mexicocovid19.plataforma.model.entity.User;

import javax.mail.MessagingException;
import java.util.List;

public interface OfertaService {
    List<Oferta> readOfertas(final Double longitude, final Double latitude, final Integer kilometers);
    Oferta createOferta(final Oferta oferta, final String username) throws PMCException;
    void finishOferta(final Integer idOferta, final User user) throws PMCException;
    void matchOferta(final Integer idOferta, final String username) throws MessagingException;


    PageResponse<OfertaDTO> readOfertasByGenericFilter(final PageRequest search);
}
