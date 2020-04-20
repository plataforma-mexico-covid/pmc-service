package mx.mexicocovid19.plataforma.controller;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.controller.dto.DisciplinaDTO;
import mx.mexicocovid19.plataforma.controller.mapper.DisciplinaMapper;
import mx.mexicocovid19.plataforma.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by betuzo on 14/05/15.
 */
@Controller
@RequestMapping(ApiController.API_PATH_PUBLIC + "/disciplina")
public class DisciplinaRestController {

    @Autowired
    private DisciplinaService disciplinaService;

    @ResponseBody
    @GetMapping(
            value = { "/" },
            produces = {"application/json;charset=UTF-8"})
    public List<DisciplinaDTO> readDisciplinas() {
        return DisciplinaMapper.from(disciplinaService.readDisciplina());
    }

}
