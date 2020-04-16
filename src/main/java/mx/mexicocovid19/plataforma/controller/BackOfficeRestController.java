package mx.mexicocovid19.plataforma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.controller.dto.AyudaDTO;
import mx.mexicocovid19.plataforma.controller.mapper.AyudaMapper;
import mx.mexicocovid19.plataforma.service.AyudaService;

/**
 * Created by mra_capri on 16/04/20.
 */
@Controller
public class BackOfficeRestController {

    @Autowired
    private AyudaService ayudaService;

    @ResponseBody
    @GetMapping(
            value = { ApiController.API_PATH_PRIVATE + "/backoffice/ayuda" },
            produces = {"application/json;charset=UTF-8"})
	public List<AyudaDTO> readAyudasByEstatusAyuda(
			@RequestParam( value = "estatus", defaultValue = "PENDIENTE") final String estatusAyuda ) {
        return AyudaMapper.from(ayudaService.readAyudasByEstatusAyuda(estatusAyuda));
    }

}
