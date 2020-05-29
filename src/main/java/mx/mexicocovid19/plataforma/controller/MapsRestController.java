package mx.mexicocovid19.plataforma.controller;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil;
import mx.mexicocovid19.plataforma.controller.dto.MapPositionsDTO;
import mx.mexicocovid19.plataforma.controller.mapper.AyudaMapper;
import mx.mexicocovid19.plataforma.controller.mapper.OfertaMapper;
import mx.mexicocovid19.plataforma.service.AyudaService;
import mx.mexicocovid19.plataforma.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping(ApiController.API_PATH_PUBLIC + "/map")
public class MapsRestController {

    @Autowired
    private AyudaService ayudaService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @ResponseBody
    @GetMapping(
            value = { "/positions" },
            produces = {"application/json;charset=UTF-8"})
    public MapPositionsDTO readAyudas(@RequestParam(value = "origenAyuda", defaultValue = "AMBOS") final String origenAyuda,
                                      @RequestParam(value = "longitude") final Double longitude,
                                      @RequestParam(value = "latitude") final Double latitude,
                                      @RequestParam(value = "kilometers") final Integer kilometers,
                                      HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        MapPositionsDTO response = new MapPositionsDTO();
        response.setAyudas(AyudaMapper.fromAndMarkByUser(ayudaService.readAyudas(origenAyuda, longitude, latitude, kilometers), username));
        response.setOfertas(OfertaMapper.fromAndMarkByUser(ofertaService.readOfertas(longitude, latitude, kilometers), username));
        return response;
    }
}
