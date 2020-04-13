package mx.mexicocovid19.plataforma.service;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.RECUPERACION_PASSWORD;
import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.REGISTRO_USUARIO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import mx.mexicocovid19.plataforma.model.entity.*;
import mx.mexicocovid19.plataforma.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.controller.dto.ChangePasswordDTO;
import mx.mexicocovid19.plataforma.controller.dto.UserDTO;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.repository.CiudadanoContactoRepository;
import mx.mexicocovid19.plataforma.model.repository.CiudadanoRepository;
import mx.mexicocovid19.plataforma.model.repository.UserRepository;
import mx.mexicocovid19.plataforma.model.repository.UserRoleRepository;
import mx.mexicocovid19.plataforma.model.repository.UserTokenRepository;
import mx.mexicocovid19.plataforma.service.enums.EnumTokenType;
import mx.mexicocovid19.plataforma.service.helper.DefaultUserServiceHelper;
import mx.mexicocovid19.plataforma.util.ErrorEnum;
@Log4j2
@Service
public class DefaultUserService implements UserService {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private CiudadanoRepository ciudadanoRepository;
    private CiudadanoContactoRepository ciudadanoContactoRepository;
    private UserTokenRepository userTokenRepository;
    private UserTokenService userTokenService;
    private MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private DefaultUserServiceHelper userServiceHelper;

    public DefaultUserService(final UserRepository userRepository, final UserRoleRepository userRoleRepository,
            final CiudadanoRepository ciudadanoRepository,
            final CiudadanoContactoRepository ciudadanoContactoRepository,
            final UserTokenRepository userTokenRepository, final UserTokenService userTokenService,
            final MailService mailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.ciudadanoRepository = ciudadanoRepository;
        this.ciudadanoContactoRepository = ciudadanoContactoRepository;
        this.userTokenRepository = userTokenRepository;
        this.userTokenService = userTokenService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void registerUser(final UserDTO userDTO, final String context) throws PMCException {
    	
    	// Ejecuta las validaciones del registro del usuario
    	userServiceHelper.validaRegistroUsuario(userDTO);
    	
    	
    	// Persiste la informacion del usuario en la base de datos        	
    	final User user = userRepository.save(getUser(userDTO));
    	final UserRole userRole = userRoleRepository.save(getUserRole(user));
    	final Ciudadano ciudadano = ciudadanoRepository.save(getCiudadano(userDTO, user));
    	ciudadanoContactoRepository.saveAll(getCiudadanoContactos(userDTO, ciudadano));
    	UserToken token = userTokenService.createUserTokenByUser(user, EnumTokenType.REGISTRO);
    	
        try {

        	// Envia la notificacion por correo electronico
        	sendMailToken(token, context, ciudadano.getNombre());
		} catch (MessagingException e) {
			throw new PMCException(ErrorEnum.ERR_GENERICO, getClass().getName(), e.getMessage());
		}
    }
    
    private void sendMailRecoveryPasswordToken(UserToken userToken, String urlConfirmToken, String nombre) throws MessagingException {
        Map<String, Object> props = new HashMap<>();
        props.put("nombre", nombre);
        props.put("link", urlConfirmToken + "/" + userToken.getToken());
        mailService.send(userToken.getUser().getUsername(), userToken.getUser().getUsername(), props, RECUPERACION_PASSWORD);
    }
    
    
    private void sendMailToken(UserToken userToken, String urlConfirmToken, String nombre) throws MessagingException {
        Map<String, Object> props = new HashMap<>();
        props.put("nombre", nombre);
        props.put("link", urlConfirmToken + "/" + userToken.getToken());

        mailService.send(userToken.getUser().getUsername(), userToken.getUser().getUsername(), props, REGISTRO_USUARIO);
    }


    private User getUser(final UserDTO userDTO) {
        final User user = new User();
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());
        user.setValidated(false);
        return user;
    }

    private UserRole getUserRole(final User user) {
        final UserRole userRole = new UserRole();
        userRole.setRole(Role.CITIZEN);
        userRole.setUser(user);
        return userRole;
    }

    private Ciudadano getCiudadano(final UserDTO userDTO, final User user) {
        final Ciudadano ciudadano = new Ciudadano();
        ciudadano.setActive(true);
        ciudadano.setMaterno(userDTO.getMaterno());
        ciudadano.setNombre(userDTO.getNombre());
        ciudadano.setPaterno(userDTO.getPaterno());
        ciudadano.setUser(user);
        return ciudadano;
    }

    private List<CiudadanoContacto> getCiudadanoContactos(final UserDTO userDTO, final Ciudadano ciudadano) {
        return userDTO.getContactInfos().stream().map(userContactInfo -> {
            final CiudadanoContacto ciudadanoContacto = new CiudadanoContacto();
            ciudadanoContacto.setCiudadano(ciudadano);
            ciudadanoContacto.setContacto(userContactInfo.getContacto());
            ciudadanoContacto.setTipoContacto(userContactInfo.getTipoContacto());
            return ciudadanoContacto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void confirmUser(final String token) throws Exception {
        UserToken userToken = userServiceHelper.getUserToken(token);
        if ( userToken == null || userToken.isValidated() || DateUtil.isExpired(userToken.getExpirationDate())) {
            throw new PMCException(ErrorEnum.ERR_CONFIRMAR_EMAIL, getClass().getName(), "Token invalido.");
        }
        userToken.getUser().setValidated(true);
        userRepository.save(userToken.getUser());
        userToken.setValidated(true);
        userTokenRepository.save(userToken);
    }

    private boolean isExpired(final LocalDateTime expirationDate) {
        return LocalDateTime.now().isAfter(expirationDate);
    }


	@Override
	public void recoveryPassword(String username, String context) throws Exception {
    	// Ejecuta las validaciones del registro del usuario
    	if ( username == null || username.isEmpty() ) {
			throw new PMCException(ErrorEnum.ERR_RECUPERACION_PASSWORD, getClass().getName(), "Especifique el usuario.");
    	}
    	
    	User user = userRepository.findByUsername(username);
    	if ( user != null ) {
        	UserToken token = userTokenService.createUserTokenByUser(user, EnumTokenType.RECUPERACION);
            try {
            	
            	Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
            	if ( ciudadano != null ) {
            		
                	// Envia la notificacion por correo al ciudadano
                	sendMailRecoveryPasswordToken(token, context, ciudadano.getNombre());
            	} else {
            		
                	// Envia la notificacion por correo a otros usuarios
            		sendMailRecoveryPasswordToken(token,  context, user.getUsername());
            	}
    		} catch (MessagingException e) {
    			throw new PMCException(ErrorEnum.ERR_GENERICO, getClass().getName(), e.getMessage());
    		}
    	} else {
    		throw new PMCException(ErrorEnum.ERR_RECUPERACION_PASSWORD, getClass().getName(), "Usuario no valido.");
    	}
	}

	@Override
	public void changePassword(ChangePasswordDTO changePwdDto) throws PMCException {

		// Obtiene el user token		
		UserToken userToken = userServiceHelper.getUserToken(changePwdDto.getToken());
		
		changePassword(userToken.getUser(), changePwdDto.getPassword(), changePwdDto.getConfirmation());
		
		// Elimina el token de recuperacion
		userTokenRepository.delete(userToken);
	}

	@Override
	public void changePassword(User username, String password, String confirmation) throws PMCException {
		
		// Valida el password
    	userServiceHelper.changePasswordValidation(username, password, confirmation);
    	
    	username.setPassword(passwordEncoder.encode(password));
    	
		// Actualiza el password
    	userRepository.save(username);
	}

}
