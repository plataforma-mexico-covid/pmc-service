package mx.mexicocovid19.plataforma.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil;
import mx.mexicocovid19.plataforma.controller.dto.AyudaDTO;
import mx.mexicocovid19.plataforma.controller.dto.MatchDTO;
import mx.mexicocovid19.plataforma.controller.mapper.AyudaMapper;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.service.AyudaService;

/**
 * Created by betuzo on 14/05/15.
 */
@Controller
public class AyudaRestController {

    @Autowired
    private AyudaService ayudaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @ResponseBody
    @GetMapping(
            value = { ApiController.API_PATH_PUBLIC + "/ayuda" },
            produces = {"application/json;charset=UTF-8"})
    public List<AyudaDTO> readAyudas(@RequestParam(value = "origenAyuda", defaultValue = "AMBOS") final String origenAyuda,
                                     @RequestParam(value = "longitude") final Double longitude,
                                     @RequestParam(value = "latitude") final Double latitude,
                                     @RequestParam(value = "kilometers") final Integer kilometers,
                                     HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return AyudaMapper.fromAndMarkByUser(ayudaService.readAyudas(origenAyuda, longitude, latitude, kilometers), username);
    }

    @ResponseBody
    @PostMapping(value = { ApiController.API_PATH_PRIVATE + "/ayuda" }, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AyudaDTO> createAyuda(@RequestBody AyudaDTO ayudaDTO, HttpServletRequest request) throws PMCException {
        String token = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        
        ResponseEntity<AyudaDTO> response = new ResponseEntity<AyudaDTO>(HttpStatus.BAD_REQUEST);
        
		Ayuda createAyuda = ayudaService.createAyuda(AyudaMapper.from(ayudaDTO), username);
		response = new ResponseEntity<AyudaDTO>(AyudaMapper.from(createAyuda), HttpStatus.OK);
        
        return response;
    }

    @ResponseBody
    @PostMapping(value = { ApiController.API_PATH_PRIVATE + "/ayuda_ciudadano" }, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AyudaDTO> createAyudaWithCiudadano(@RequestBody AyudaDTO ayudaDTO) throws PMCException {
        ResponseEntity<AyudaDTO> response = new ResponseEntity<AyudaDTO>(HttpStatus.BAD_REQUEST);

        Ayuda createAyuda = ayudaService.createAyudaAndCiudadano(AyudaMapper.from(ayudaDTO));
        response = new ResponseEntity<AyudaDTO>(AyudaMapper.from(createAyuda), HttpStatus.OK);

        return response;
    }

    @ResponseBody
    @PostMapping(
            value = { ApiController.API_PATH_PRIVATE + "/ayuda/{ayuda}/match" },
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> matchAyuda(@PathVariable(value = "ayuda") Integer idAyuda, @RequestBody MatchDTO matchDTO) throws MessagingException {
        ayudaService.matchAyuda(idAyuda, matchDTO.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
