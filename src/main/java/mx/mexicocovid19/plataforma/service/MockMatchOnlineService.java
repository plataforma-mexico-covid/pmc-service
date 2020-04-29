package mx.mexicocovid19.plataforma.service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Profile("matchoff")
public class MockMatchOnlineService implements MatchOnlineService {
    @Override
    public void verifyMatch(Ayuda ayuda) {

    }
}
