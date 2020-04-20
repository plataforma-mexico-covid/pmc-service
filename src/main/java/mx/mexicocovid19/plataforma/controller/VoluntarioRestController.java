package mx.mexicocovid19.plataforma.controller;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.controller.dto.VoluntarioDTO;
import mx.mexicocovid19.plataforma.controller.mapper.VoluntarioMapper;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Voluntario;
import mx.mexicocovid19.plataforma.service.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by betuzo on 14/05/15.
 */
@Controller
public class VoluntarioRestController {

    @Autowired
    private VoluntarioService voluntarioService;


    @ResponseBody
    @PostMapping(value = { ApiController.API_PATH_PRIVATE + "/voluntario" }, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<VoluntarioDTO> createVoluntario(@RequestBody VoluntarioDTO voluntarioDTO) throws PMCException {
        ResponseEntity<VoluntarioDTO> response = new ResponseEntity<VoluntarioDTO>(HttpStatus.BAD_REQUEST);

        Voluntario voluntario = voluntarioService.crearVoluntario(VoluntarioMapper.from(voluntarioDTO));
        response = new ResponseEntity<VoluntarioDTO>(VoluntarioMapper.from(voluntario), HttpStatus.OK);

        return response;
    }

}
