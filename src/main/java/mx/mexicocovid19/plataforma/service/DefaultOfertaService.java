package mx.mexicocovid19.plataforma.service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.controller.dto.OfertaDTO;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageRequest;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageResponse;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.*;
import mx.mexicocovid19.plataforma.model.repository.CiudadanoRepository;
import mx.mexicocovid19.plataforma.model.repository.GeoLocationRepository;
import mx.mexicocovid19.plataforma.model.repository.OfertaRepository;
import mx.mexicocovid19.plataforma.service.helper.GroseriasHelper;
import mx.mexicocovid19.plataforma.util.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.*;

@Log4j2
@Service
public class DefaultOfertaService implements OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @Autowired
    private MailService mailService;

    @Override
    public List<Oferta> readOfertas(Double longitude, Double latitude, Integer kilometers) {
        return ofertaRepository.findByAllInsideOfKilometers(latitude, longitude, kilometers);
    }

    @Override
    public Oferta createOferta(Oferta oferta, String username) throws PMCException {
        try {
            User user = new User();
            user.setUsername(username);
            Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
            Oferta ofertaTmp = saveOferta(oferta, ciudadano);

            // Envia notificacion por correo electronic
            Map<String, Object> props = new HashMap<>();
            props.put("nombre", ofertaTmp.getCiudadano().getNombreCompleto());
            props.put("descripcion", ofertaTmp.getNombre());
            mailService.send(ciudadano.getUser().getUsername(), ciudadano.getUser().getUsername(), props, REGISTRO_OFERTA);

            return ofertaTmp;
        } catch (MessagingException e) {
            log.info(e.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", e.getMessage());
        }
    }

    @Override
    public PageResponse<OfertaDTO> readOfertasByGenericFilter(PageRequest search) {
        return null;
    }

    @Transactional
    private Oferta saveOferta(final Oferta ayuda, final Ciudadano ciudadano) throws PMCException {
        GeoLocation ubicacion = geoLocationRepository.save(ayuda.getUbicacion());
        ayuda.setUbicacion(ubicacion);
        ayuda.setCiudadano(ciudadano);

        if ( !GroseriasHelper.evaluarTexto(ayuda.getDescripcion()) ) {
            ayuda.setEstatusOferta(EstatusAyuda.NUEVA);
            Oferta ayudaTmp = ofertaRepository.save(ayuda);

            return ayudaTmp;
        } else {
            throw new PMCException(ErrorEnum.ERR_LENGUAJE_SOEZ, "DefaultAyudaService");
        }
    }
}
