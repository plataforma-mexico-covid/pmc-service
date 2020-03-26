package mx.mexicocovid19.plataforma.controller;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.controller.dto.AyudaDTO;
import mx.mexicocovid19.plataforma.controller.mapper.AyudaMapper;
import mx.mexicocovid19.plataforma.service.AyudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by betuzo on 14/05/15.
 */
@Controller
@RequestMapping(ApiController.API_PATH_PRIVATE + "/ayuda")
public class AyudaRestController {

    @Autowired
    private AyudaService ayudaService;


    @ResponseBody
    @GetMapping(
            value = { "/" },
            produces = {"application/json;charset=UTF-8"})
    public List<AyudaDTO> readAyudas(@RequestParam(value = "origenAyuda", defaultValue = "AMBOS") final String origenAyuda,
                                     @RequestParam(value = "longitude") final BigDecimal longitude,
                                     @RequestParam(value = "latitude") final BigDecimal latitude,
                                     @RequestParam(value = "kilometers") final Integer kilometers) {
        return AyudaMapper.from(ayudaService.readAyudas(origenAyuda, longitude, latitude, kilometers));
    }
}