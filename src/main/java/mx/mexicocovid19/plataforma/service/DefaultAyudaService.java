package mx.mexicocovid19.plataforma.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.MessagingException;

import com.google.gson.Gson;
import mx.mexicocovid19.plataforma.controller.dto.AyudaDTO;
import mx.mexicocovid19.plataforma.controller.dto.InfoSensibleDTO;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageRequest;
import mx.mexicocovid19.plataforma.controller.dto.pagination.PageResponse;
import mx.mexicocovid19.plataforma.controller.mapper.AyudaMapper;
import mx.mexicocovid19.plataforma.controller.mapper.InfoSensibleMapper;
import mx.mexicocovid19.plataforma.model.entity.*;
import mx.mexicocovid19.plataforma.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.service.helper.AyudaRateRegisterEvaluationServiceHelper;
import mx.mexicocovid19.plataforma.service.helper.GroseriasHelper;
import mx.mexicocovid19.plataforma.util.ErrorEnum;
import org.springframework.transaction.annotation.Transactional;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.*;
import static mx.mexicocovid19.plataforma.util.ErrorEnum.ERR_USUARIO_AYUDA_NO_AUTORIZADO;

@Log4j2
@Service
public class DefaultAyudaService implements AyudaService {

    @Autowired
    private AyudaRepository ayudaRepository;

    @Autowired
    private PeticionRepository peticionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    @Autowired
    private CiudadanoContactoRepository ciudadanoContactoRepository;

    @Autowired
    private MailService mailService;
    
    @Autowired
    private AyudaRateRegisterEvaluationServiceHelper ayudaRateRegisterEvaluation;

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private MatchOnlineService matchOnlineService;

    @Override
    public List<Ayuda> readAyudas(String origenAyuda, Double longitude, Double latitude, Integer kilometers) {
        try {
            OrigenAyuda origenAyudaValid = OrigenAyuda.valueOf(origenAyuda);
            return ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(latitude, longitude, kilometers, origenAyudaValid);
        } catch (IllegalArgumentException ex) {
            return ayudaRepository.findByAllInsideOfKilometers(latitude, longitude, kilometers);
        }
    }

    @Override
    public Ayuda createAyuda(final Ayuda ayuda, final String username) throws PMCException {
        try {
    		// Valida el numero de ayudas que ha registrado el usuario firmado
    		if ( ayudaRateRegisterEvaluation.isMaximumRequestsPerHourExceeded(username) ) {
    			throw new PMCException(ErrorEnum.ERR_MAX_AYUDA, "DefaultAyudaService");
    		}
        	User user = new User();
        	user.setUsername(username);
        	Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
            Ayuda ayudaTmp = saveAyuda(ayuda, ciudadano);

            // Envia notificacion por correo electronic
            Map<String, Object> props = new HashMap<>();
            props.put("nombre", ayudaTmp.getCiudadano().getNombreCompleto());
            TipoEmailEnum tipoEmail = ayudaTmp.getOrigenAyuda() == OrigenAyuda.SOLICITA ? SOLICITA_AYUDA : OFRECE_AYUDA;
            matchOnlineService.verifyMatchAutomatic(ayudaTmp);
            mailService.send(ciudadano.getUser().getUsername(), ciudadano.getUser().getUsername(), props, tipoEmail);

            return ayudaTmp;

		} catch (MessagingException e) {
			log.info(e.getMessage());
			throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", e.getMessage());
		}
    }

    @Override
    @Transactional
    public Ayuda updateAyuda(Ayuda ayuda, User user) throws PMCException {
        Ayuda ayudaSave = ayudaRepository.getOne(ayuda.getId());
        if (!allowFinishAyuda(user, ayudaSave)){
            log.info(ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
        }
        ayudaSave.setDescripcion(ayuda.getDescripcion());
        ayudaSave.setOrigenAyuda(ayuda.getOrigenAyuda());
        ayudaSave.setTipoAyuda(ayuda.getTipoAyuda());

        if ( !GroseriasHelper.evaluarTexto(ayudaSave.getDescripcion()) ) {
            return ayudaRepository.save(ayudaSave);
        } else {
            throw new PMCException(ErrorEnum.ERR_LENGUAJE_SOEZ, "DefaultAyudaService");
        }
    }

    @Override
    public Ayuda createAyudaAndCiudadano(final Ayuda ayuda) throws PMCException {
        try {
            Ayuda ayudaStore = saveAyudaAndCiudadano(ayuda);
            matchOnlineService.verifyMatchAutomatic(ayudaStore);
            return ayudaStore;
        } catch (Exception e){
            log.info(e.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService createAyudaAndCiudadano", e.getMessage());
        }
    }

    @Override
    public void matchAyuda(Integer idAyuda, String username) throws MessagingException {
        Ayuda ayuda = saveMatchAyuda(idAyuda);
        User user = new User();
        user.setUsername(username);
        Optional<Ciudadano> ciudadanoAyuda = ciudadanoRepository.findById(ayuda.getCiudadano().getId());
        Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        Map<String, Object> props = createInfoToEmail(ayuda,
                ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ciudadanoAyuda.get() : ciudadano,
                ayuda.getOrigenAyuda() == OrigenAyuda.SOLICITA ? ciudadanoAyuda.get() : ciudadano);
        String to = ciudadanoAyuda.get().getUser() != null? ciudadanoAyuda.get().getUser().getUsername() : user.getUsername();
        String cc = ciudadanoAyuda.get().getUser() != null? user.getUsername() : null;
        matchOnlineService.verifyMatchManual(ayuda, ciudadano);
        mailService.send(to, cc, props, MATCH_AYUDA);
    }

    @Override
    public String getOrigenByRole(List<GrantedAuthority> roles, final String origen) {
        log.info("getOrigenByRole: roles: " + new Gson().toJson(roles) + " origen: " + origen);
        for (int i = 0 ; i < roles.size() ; i++ ){
            String role = (String) ((Map) roles.get(i)).get("authority");
            if(role.equals(Role.VOLUNTARY.name())
                    || role.equals(Role.CHATBOT.name())){
                return role;
            } else if (role.equals(Role.LANDING.name())) {
                return (origen != null && !origen.isEmpty()) ? origen : Role.LANDING.name();
            }
        }
        return origen;
    }

    @Override
    @Transactional
    public void finishAyuda(Integer idAyuda, User user) throws PMCException {
        Ayuda ayuda = ayudaRepository.getOne(idAyuda);
        if (!allowFinishAyuda(user, ayuda)){
            log.info(ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", ERR_USUARIO_AYUDA_NO_AUTORIZADO.getMessage());
        }
        ayuda.setEstatusAyuda(EstatusAyuda.COMPLETEDA);
        ayudaRepository.save(ayuda);
    }

    @Override
    @Transactional
    public void finishAyudaByContacto(final String contacto) {
        List<CiudadanoContacto> contactos = ciudadanoContactoRepository.findAllByContacto(contacto);
        for (CiudadanoContacto ccontacto: contactos) {
            List<Ayuda> ayudas = ayudaRepository.findByCiudadano(ccontacto.getCiudadano());
            for (Ayuda ayuda: ayudas) {
                ayuda.setEstatusAyuda(EstatusAyuda.COMPLETEDA);
                ayudaRepository.save(ayuda);
            }
        }
    }

    @Override
    public List<Ayuda> readAyudaByContacto(String contacto) {
        List<Ayuda> result = new ArrayList<>();
        List<CiudadanoContacto> contactos = ciudadanoContactoRepository.findAllByContacto(contacto);
        for (CiudadanoContacto ccontacto: contactos) {
            List<Ayuda> ayudas = ayudaRepository.findByCiudadano(ccontacto.getCiudadano());
            result.addAll(ayudas);
        }
        return result;
    }

    @Override
    public InfoSensibleDTO readSensitiveInfo(Integer idAyuda) {
        Ayuda ayuda = ayudaRepository.getOne(idAyuda);
        List<Peticion> matchs = peticionRepository.findAllByAyuda(ayuda);
        return InfoSensibleMapper.from(ayuda.getCiudadano(), matchs);
    }

    @Transactional
    private Ayuda saveAyuda(final Ayuda ayuda, final Ciudadano ciudadano) throws PMCException {
        GeoLocation ubicacion = geoLocationRepository.save(ayuda.getUbicacion());
        ayuda.setUbicacion(ubicacion);
        ayuda.setCiudadano(ciudadano);
        ayuda.setOrigen("PLATFORM");

        if ( !GroseriasHelper.evaluarTexto(ayuda.getDescripcion()) ) {
            ayuda.setEstatusAyuda(EstatusAyuda.NUEVA);
            Ayuda ayudaTmp = ayudaRepository.save(ayuda);

            return ayudaTmp;
        } else {
            throw new PMCException(ErrorEnum.ERR_LENGUAJE_SOEZ, "DefaultAyudaService");
        }
    }

    @Transactional
    private Ayuda saveAyudaAndCiudadano(final Ayuda ayuda) throws PMCException {
        try {
            Set<CiudadanoContacto> contactos = ayuda.getCiudadano().getContactos();
            Ciudadano ciudadano = null;
            for (CiudadanoContacto it: contactos) {
                List<CiudadanoContacto> cc = ciudadanoContactoRepository.findAllByContacto(it.getContacto());
                if (cc == null || cc.size() == 0) {
                    if (ciudadano == null) {
                        ciudadano = saveCiudadadoByContacto(ayuda.getCiudadano(), it.getContacto());
                    }

                    it.setCiudadano(ciudadano);
                    ciudadanoContactoRepository.save(it);
                } else if (cc != null && cc.size() > 0 ){
                    ciudadano = cc.get(0).getCiudadano();
                }
            }

            GeoLocation location = geoLocationRepository.save(fillGeoLocation(ayuda.getUbicacion()));
            ayuda.setFechaRegistro(LocalDateTime.now());
            ayuda.setCiudadano(ciudadano);
            ayuda.setUbicacion(location);
            ayuda.setEstatusAyuda(EstatusAyuda.NUEVA);
            ayuda.setActive(true);
            return ayudaRepository.save(ayuda);
        } catch (Exception e){
            log.info(e.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService saveAyudaAndCiudadano", e.getMessage());
        }
    }

    private Ciudadano saveCiudadadoByContacto(final Ciudadano ciudadano, final String contacto) {
        User user = userRepository.findByUsername(contacto);
        if (user != null) {
            Ciudadano ciudadanoStore = ciudadanoRepository.findByUser(user);
            if (ciudadanoStore != null) return ciudadanoStore;
        }

        ciudadano.setContactos(null);
        ciudadano.setActive(true);
        ciudadano.setUser(user);
        return ciudadanoRepository.save(ciudadano);
    }

    private GeoLocation fillGeoLocation(GeoLocation geoLocation){
        if ((geoLocation.getLatitude() == null || geoLocation.getLongitude() == null
                || geoLocation.getLatitude() == 0 || geoLocation.getLongitude() == 0)
                && !geoLocation.getCodigoPostal().isEmpty()){
            Map<String, Double> loc = geoLocationService.getPositionByPostalCode(geoLocation.getCodigoPostal());
            geoLocation.setLatitude(loc.get("lat"));
            geoLocation.setLongitude(loc.get("lng"));
        }
        return geoLocation;
    }

    @Transactional
    private Ayuda saveMatchAyuda(Integer idAyuda) {
        Ayuda ayuda = ayudaRepository.getOne(idAyuda);
        ayuda.setEstatusAyuda(EstatusAyuda.EN_PROGRESO);
        return ayudaRepository.save(ayuda);
    }

    private boolean allowFinishAyuda(final User user, final Ayuda ayuda) throws PMCException {
        Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        for (UserRole role: user.getUserRole()) {
            if(role.getRole()==Role.MANAGER || role.getRole()==Role.VOLUNTARY) {
                return true;
            }
        }
        return ayuda.getCiudadano().getId() == ciudadano.getId();
    }

    private Map<String, Object> createInfoToEmail(Ayuda ayuda, Ciudadano ofrece, Ciudadano solicita){
        String contactoOfrece = ofrece.getContactos().stream()
                .map(contacto -> contacto.getTipoContacto() + " : " + contacto.getContacto() + " ")
                .reduce("", (partialString, element) -> partialString + element);
        String contactoSolicita = solicita.getContactos().stream()
                .map(contacto -> contacto.getTipoContacto() + " : " + contacto.getContacto() + " ")
                .reduce("", (partialString, element) -> partialString + element);
        Map<String, Object> props = new HashMap<>();
        props.put("ayuda", ayuda.getDescripcion());
        props.put("nombre-ofrece", ofrece.getNombreCompleto());
        props.put("email-ofrece", ofrece.getUser() != null ? ofrece.getUser().getUsername() : "N/A");
        props.put("contacto-ofrece", contactoOfrece);
        props.put("nombre-solicita", solicita.getNombreCompleto());
        props.put("email-solicita", solicita.getUser() != null ? solicita.getUser().getUsername() : "N/A");
        props.put("contacto-solicita", contactoSolicita);
        return props;
    }

	@Override
	public List<Ayuda> readAyudasByEstatusAyuda(String estatusAyuda) {
		EstatusAyuda value = EstatusAyuda.valueOf(estatusAyuda);
		return ayudaRepository.findByEstatusAyuda(value);
	}


    @Override
    public PageResponse<AyudaDTO> readAyudasByGenericFilter(final PageRequest pageRequest) {
        PageResponse<AyudaDTO> response = new PageResponse<>();
        String search = pageRequest.getSearch().getValue();
        OrigenAyuda origenAyuda = null;
        EstatusAyuda estatusAyuda = null;
        try {
            origenAyuda = OrigenAyuda.valueOf(search);
            search = "";
        }catch (Exception ex){
            log.info("readAyudasByGenericFilter origenAyuda: " + origenAyuda);
        }
        try {
            estatusAyuda = EstatusAyuda.valueOf(search);
            search = "";
        }catch (Exception ex){
            log.info("readAyudasByGenericFilter estatusAyuda: " + estatusAyuda);
        }
        int page = (int) (pageRequest.getStart() / pageRequest.getLength());
        int size = (int) pageRequest.getLength();
        Page<Ayuda> ayudas = ayudaRepository.findByFilter(estatusAyuda, origenAyuda, search, org.springframework.data.domain.PageRequest.of(page, size));
        response.setRecordsTotal(ayudas.getTotalElements());
        response.setRecordsFiltered(ayudas.getTotalElements());
        response.setDraw(pageRequest.getDraw());
        response.setData(AyudaMapper.from(ayudas.getContent()));
        return response;
    }
}
