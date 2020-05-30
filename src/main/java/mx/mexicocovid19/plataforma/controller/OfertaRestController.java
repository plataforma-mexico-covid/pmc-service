package mx.mexicocovid19.plataforma.controller;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil;
import mx.mexicocovid19.plataforma.controller.dto.OfertaDTO;
import mx.mexicocovid19.plataforma.controller.mapper.OfertaMapper;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Oferta;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by betuzo on 14/05/15.
 */
@Log4j2
@Controller
@RequestMapping(ApiController.API_PATH_PRIVATE + "/oferta")
public class OfertaRestController {

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @ResponseBody
    @PostMapping(value = { "" }, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<OfertaDTO> createOferta(@RequestBody OfertaDTO ofertaDTO, HttpServletRequest request) throws PMCException {
        String token = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        
        ResponseEntity<OfertaDTO> response = new ResponseEntity<OfertaDTO>(HttpStatus.BAD_REQUEST);
        
		Oferta createOferta = ofertaService.createOferta(OfertaMapper.from(ofertaDTO), username);
		response = new ResponseEntity<OfertaDTO>(OfertaMapper.from(createOferta), HttpStatus.OK);
        
        return response;
    }

    @ResponseBody
    @PostMapping(
            value = { "/{oferta}/finish" },
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> finalizarOferta(@PathVariable(value = "oferta") Integer idOferta, User user) throws PMCException {
        ofertaService.finishOferta(idOferta, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
