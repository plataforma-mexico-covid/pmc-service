package mx.mexicocovid19.plataforma.service;

import java.time.LocalDateTime;
import java.util.*;

import javax.mail.MessagingException;

import mx.mexicocovid19.plataforma.model.entity.*;
import mx.mexicocovid19.plataforma.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.service.helper.AyudaRateRegisterEvaluationServiceHelper;
import mx.mexicocovid19.plataforma.service.helper.GroseriasHelper;
import mx.mexicocovid19.plataforma.util.ErrorEnum;
import org.springframework.transaction.annotation.Transactional;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.*;

@Log4j2
@Service
public class DefaultAyudaService implements AyudaService {

    @Autowired
    private AyudaRepository ayudaRepository;

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
    private PeticionRepository peticionRepository;
    
    @Autowired
    private AyudaRateRegisterEvaluationServiceHelper ayudaRateRegisterEvaluation;

    @Autowired
    private GeoLocationService geoLocationService;

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
    @Transactional
    public Ayuda createAyuda(final Ayuda ayuda, final String username) throws PMCException {
        
        try {
        	
    		// Valida el numero de ayudas que ha registrado el usuario firmado
    		if ( ayudaRateRegisterEvaluation.isMaximumRequestsPerHourExceeded(username) ) {
    			throw new PMCException(ErrorEnum.ERR_MAX_AYUDA, "DefaultAyudaService");
    		}
    		
    		
        	User user = new User();
        	user.setUsername(username);
        	Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        	GeoLocation ubicacion = geoLocationRepository.save(ayuda.getUbicacion());
        	ayuda.setUbicacion(ubicacion);
        	ayuda.setCiudadano(ciudadano);
            ayuda.setOrigen("PLATFORM");
        	
        	if ( !GroseriasHelper.evaluarTexto(ayuda.getDescripcion()) ) {
                ayuda.setEstatusAyuda(EstatusAyuda.NUEVA);
        		Ayuda ayudaTmp = ayudaRepository.save(ayuda);

        		// Envia notificacion por correo electronic
        		Map<String, Object> props = new HashMap<>();
        		props.put("nombre", ayuda.getCiudadano().getNombreCompleto());
        		TipoEmailEnum tipoEmail = ayuda.getOrigenAyuda() == OrigenAyuda.SOLICITA ? SOLICITA_AYUDA : OFRECE_AYUDA;
                mailService.send(ciudadano.getUser().getUsername(), ciudadano.getUser().getUsername(), props, tipoEmail);

        		return ayudaTmp;	
        	} else {        		
        		throw new PMCException(ErrorEnum.ERR_LENGUAJE_SOEZ, "DefaultAyudaService");	
        	}			
		} catch (MessagingException e) {
			log.info(e.getMessage());
			throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", e.getMessage());
		}
    }

    @Override
    @Transactional
    public Ayuda createAyudaAndCiudadano(final Ayuda ayuda) throws PMCException {
        try {
            Set<CiudadanoContacto> contactos = ayuda.getCiudadano().getContactos();
            Ciudadano ciudadano = ayuda.getCiudadano();
            ciudadano.setContactos(null);
            ciudadano.setActive(true);
            Ciudadano ciudadanoSave = ciudadanoRepository.save(ayuda.getCiudadano());
            contactos.forEach(it -> {
                it.setCiudadano(ciudadanoSave);
                ciudadanoContactoRepository.save(it);
            });
            GeoLocation location = geoLocationRepository.save(fillGeoLocation(ayuda.getUbicacion()));
            ayuda.setFechaRegistro(LocalDateTime.now());
            ayuda.setCiudadano(ciudadanoSave);
            ayuda.setUbicacion(location);
            ayuda.setEstatusAyuda(EstatusAyuda.NUEVA);
            ayuda.setActive(true);
            return ayudaRepository.save(ayuda);
        } catch (Exception e){
            log.info(e.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", e.getMessage());
        }
    }

    private GeoLocation fillGeoLocation(GeoLocation geoLocation){
        if ((geoLocation.getLatitude() <= 0 || geoLocation.getLongitude() <= 0)
                && !geoLocation.getCodigoPostal().isEmpty()){
            Map<String, Double> loc = geoLocationService.getPositionByPostalCode(geoLocation.getCodigoPostal());
            geoLocation.setLatitude(loc.get("lat"));
            geoLocation.setLongitude(loc.get("lng"));
        }
        return geoLocation;
    }

    @Override
    @Transactional
    public void matchAyuda(Integer idAyuda, String username) throws MessagingException {
        Ayuda ayuda = ayudaRepository.getOne(idAyuda);
        User user = new User();
        user.setUsername(username);
        Optional<Ciudadano> ciudadanoAyuda = ciudadanoRepository.findById(ayuda.getCiudadano().getId());
        Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
        Peticion peticion = new Peticion();
        peticion.setAyuda(ayuda);
        peticion.setCiudadano(ciudadano);
        peticion.setFechaPeticion(LocalDateTime.now());
        peticionRepository.save(peticion);
        ayuda.setEstatusAyuda(EstatusAyuda.EN_PROGRESO);
        ayudaRepository.save(ayuda);
        Map<String, Object> props = createInfoToEmail(ayuda,
                ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ciudadanoAyuda.get() : ciudadano,
                ayuda.getOrigenAyuda() == OrigenAyuda.SOLICITA ? ciudadano : ciudadanoAyuda.get());
        mailService.send(ciudadanoAyuda.get().getUser().getUsername(), user.getUsername(), props, MATCH_AYUDA);
    }

    @Override
    public String getOrigenByRole(List<GrantedAuthority> roles, final String origen) {
        for (int i = 0 ; i < roles.size() ; i++ ){
            String role = (String) ((Map) roles.get(i)).get("authority");
            if(role.equals(Role.VOLUNTARY.name())
                    || role.equals(Role.CHATBOT.name())){
                return role;
            }
        }
        return origen;
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
        props.put("email-ofrece", ofrece.getUser().getUsername());
        props.put("contacto-ofrece", contactoOfrece);
        props.put("nombre-solicita", solicita.getNombreCompleto());
        props.put("email-solicita", solicita.getUser().getUsername());
        props.put("contacto-solicita", contactoSolicita);
        return props;
    }

	@Override
	public List<Ayuda> readAyudasByEstatusAyuda(String estatusAyuda) {
		EstatusAyuda value = EstatusAyuda.valueOf(estatusAyuda);
		return ayudaRepository.findByEstatusAyuda(value);
	}


    @Override
    public List<Ayuda> readAyudas() {
        return new ArrayList<>(); //ayudaRepository.findAll().subList(0, 100);
    }
}
