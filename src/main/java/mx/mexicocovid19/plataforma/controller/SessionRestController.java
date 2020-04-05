package mx.mexicocovid19.plataforma.controller;

import mx.mexicocovid19.plataforma.controller.dto.LoginRequest;
import mx.mexicocovid19.plataforma.controller.dto.LoginResponse;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.service.CiudadanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil;
import mx.mexicocovid19.plataforma.config.security.JwtUserFactory;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by betuzo on 07/04/15.
 */
@Controller
@RequestMapping(value = "")
public class SessionRestController {

    @Autowired
    UserRepository userService;

    @Autowired
    CiudadanoService ciudadanoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    String tokenHeader;

    @ResponseBody
    @RequestMapping(
            value = { ApiController.API_PATH_PUBLIC + "/login" },
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"})
    public LoginResponse login(
            @RequestBody LoginRequest user, HttpServletRequest httpRequest) {
    	
    	String token = "";
    	
        Device device = DeviceUtils.getCurrentDevice(httpRequest);

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            throw new BadCredentialsException("Username or password not found.");
        }

        final User usuario = userService.findByUsername(user.getUsername());
        if (usuario == null || !passwordEncoder.matches(user.getPassword(), usuario.getPassword())){
            throw new BadCredentialsException("Username or password incorrect.");
        }
        if (!usuario.isValidated()){
            throw new BadCredentialsException("Por favor valida tu usuario, abriendo el link que se te envio a tu email.");
        }
        List<GrantedAuthority> roles = JwtUserFactory.mapToGrantedAuthorities(usuario.getUserRole());
        
        Ciudadano ciudadano = ciudadanoService.readCiudadano(usuario);        
        
        // El usuario no es ciudadano
        if ( ciudadano == null ) {
        	
        	token = jwtTokenUtil.generateToken(usuario.getUsername(), "", device, roles);
        	return createSessionDTO(usuario.getUsername(), "", roles, token);
        	
        } else {
        	// ciudadano
        	token = jwtTokenUtil.generateToken(usuario.getUsername(), ciudadano.getNombre(), device, roles);
        	return createSessionDTO(usuario.getUsername(), ciudadano.getNombre(), roles, token);
        }
    }

    @ResponseBody
    @RequestMapping(
            value = { ApiController.API_PATH_PRIVATE + "/valid/token" },
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public LoginResponse isValidToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(this.tokenHeader);
        return createSessionDTO(this.jwtTokenUtil.getUsernameFromToken(token), this.jwtTokenUtil.getFullnameFromToken(token),
                this.jwtTokenUtil.getRolesFromToken(token), token);
    }

    private LoginResponse createSessionDTO(
            final String username, final String fullname, final List<GrantedAuthority> roles, final String token){
        final LoginResponse sessionDTO = new LoginResponse();
        sessionDTO.setUsername(username);
        sessionDTO.setEmail(username);
        sessionDTO.setFullname(fullname);
        sessionDTO.setRoles(roles);
        sessionDTO.setToken(token);

        return sessionDTO;
    }
}
