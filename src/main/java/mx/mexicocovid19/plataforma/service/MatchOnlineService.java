package mx.mexicocovid19.plataforma.service;

import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;

public interface MatchOnlineService {
    void verifyMatchAutomatic(final Ayuda ayuda);
    void verifyMatchManual(final Ayuda ayuda, final Ciudadano ciudadano);
}
