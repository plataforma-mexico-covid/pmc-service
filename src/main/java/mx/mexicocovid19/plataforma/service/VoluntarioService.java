package mx.mexicocovid19.plataforma.service;

import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Voluntario;

public interface VoluntarioService {
    Voluntario crearVoluntario(Voluntario voluntario) throws PMCException;
}
