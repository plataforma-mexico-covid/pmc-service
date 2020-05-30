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
import java.util.Optional;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.*;
import static mx.mexicocovid19.plataforma.util.ErrorEnum.ERR_USUARIO_AYUDA_NO_AUTORIZADO;

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
    public void finishOferta(Integer idOferta, User user) throws PMCException {
        Oferta oferta = ofertaRepository.getOne(idOferta);
        if (!allowFinishOferta(user, oferta)){
            log.info(ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
        }
        oferta.setEstatusOferta(EstatusAyuda.COMPLETEDA);
        ofertaRepository.save(oferta);
    }

    @Override
    public void matchOferta(Integer idOferta, String username) throws MessagingException {
        Oferta oferta = saveMatchOferta(idOferta);
        User user = new User();
        user.setUsername(username);
        Optional<Ciudadano> ciudadanoAyuda = ciudadanoRepository.findById(oferta.getCiudadano().getId());
        Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        Map<String, Object> props = createInfoToEmail(oferta, ciudadanoAyuda.get(), ciudadano);
        String to = ciudadanoAyuda.get().getUser() != null? ciudadanoAyuda.get().getUser().getUsername() : user.getUsername();
        String cc = ciudadanoAyuda.get().getUser() != null? user.getUsername() : null;
        mailService.send(to, cc, props, MATCH_OFERTA);
    }

    @Override
    public PageResponse<OfertaDTO> readOfertasByGenericFilter(PageRequest search) {
        return null;
    }

    @Transactional
    private Oferta saveMatchOferta(Integer idOferta) {
        Oferta oferta = ofertaRepository.getOne(idOferta);
        oferta.setEstatusOferta(EstatusAyuda.EN_PROGRESO);
        return ofertaRepository.save(oferta);
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

    private Map<String, Object> createInfoToEmail(Oferta oferta, Ciudadano ofrece, Ciudadano solicita){
        String contactoOfrece = ofrece.getContactos().stream()
                .map(contacto -> contacto.getTipoContacto() + " : " + contacto.getContacto() + " ")
                .reduce("", (partialString, element) -> partialString + element);
        String contactoSolicita = solicita.getContactos().stream()
                .map(contacto -> contacto.getTipoContacto() + " : " + contacto.getContacto() + " ")
                .reduce("", (partialString, element) -> partialString + element);
        Map<String, Object> props = new HashMap<>();
        props.put("oferta", oferta.getNombre());
        props.put("oferta-desc", oferta.getDescripcion());
        props.put("nombre-ofrece", ofrece.getNombreCompleto());
        props.put("email-ofrece", ofrece.getUser() != null ? ofrece.getUser().getUsername() : "N/A");
        props.put("contacto-ofrece", contactoOfrece);
        props.put("nombre-solicita", solicita.getNombreCompleto());
        props.put("email-solicita", solicita.getUser() != null ? solicita.getUser().getUsername() : "N/A");
        props.put("contacto-solicita", contactoSolicita);
        return props;
    }

    private boolean allowFinishOferta(final User user, final Oferta oferta) {
        Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        for (UserRole role: user.getUserRole()) {
            if(role.getRole()==Role.MANAGER || role.getRole()==Role.VOLUNTARY) {
                return true;
            }
        }
        return oferta.getCiudadano().getId() == ciudadano.getId();
    }
}
