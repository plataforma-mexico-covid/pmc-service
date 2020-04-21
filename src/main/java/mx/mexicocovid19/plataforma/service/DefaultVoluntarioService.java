package mx.mexicocovid19.plataforma.service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.*;
import mx.mexicocovid19.plataforma.model.repository.*;
import mx.mexicocovid19.plataforma.util.ErrorEnum;
import mx.mexicocovid19.plataforma.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.*;

@Log4j2
@Service
public class DefaultVoluntarioService implements VoluntarioService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    GeoLocationRepository geoLocationRepository;

    @Autowired
    CiudadanoRepository ciudadanoRepository;

    @Autowired
    VoluntarioRepository voluntarioRepository;

    @Autowired
    CiudadanoContactoRepository ciudadanoContactoRepository;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RandomPasswordGenerator passwordGenerator;

    @Override
    @Transactional
    public Voluntario crearVoluntario(Voluntario voluntario) throws PMCException {
        try {
            String password = passwordGenerator.generatePassayPassword();
            User user = saveUserVoluntario(voluntario.getCiudadano().getUser(), password);

            GeoLocation ubicacion = geoLocationRepository.save(voluntario.getUbicacion());
            voluntario.getCiudadano().setUser(user);
            Set<CiudadanoContacto> contactos = voluntario.getCiudadano().getContactos();
            voluntario.getCiudadano().setContactos(null);
            Ciudadano ciudadano = ciudadanoRepository.save(voluntario.getCiudadano());
            contactos.forEach(it -> {
                it.setCiudadano(ciudadano);
                ciudadanoContactoRepository.save(it);
            });
            voluntario.setCiudadano(ciudadano);
            voluntario.setUbicacion(ubicacion);
            voluntario.setFechaRegistro(LocalDateTime.now());
            voluntario.setActive(true);
            voluntario = voluntarioRepository.save(voluntario);

            mailService.send(user.getUsername(), user.getUsername(), createInfoToEmail(voluntario, password), REGISTRO_VOLUNTARIO);
            return voluntario;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new PMCException(ErrorEnum.ERR_GENERICO, "DefaultAyudaService", ex.getMessage());
        }
    }

    private User saveUserVoluntario(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        user.setValidated(true);
        user.setEnabled(true);
        user = userRepository.save(user);
        UserRole role = new UserRole();
        role.setUser(user);
        role.setRole(Role.PRE_VOLUNTARY);
        userRoleRepository.save(role);
        return user;
    }

    private Map<String, Object> createInfoToEmail(Voluntario voluntario, String password){
        Map<String, Object> props = new HashMap<>();
        props.put("nombre", voluntario.getCiudadano().getNombre());
        props.put("password", password);
        return props;
    }
}
