package mx.mexicocovid19.plataforma.service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@Profile("matchon")
public class AnalizerMatchOnlineService implements MatchOnlineService {

    @Value("${plataforma.analizer.url}")
    private String analizerUrl;

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void verifyMatch(final Ayuda ayuda) {
        try{
            Map<String, Object> request = createRequest(ayuda);
            ResponseEntity<Void> response
                    = restTemplate.postForEntity(this.analizerUrl + "/api/v1/internal/analizer/reminder", request, Void.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return;
            log.error("FAIL Match Ayuda Online: " + ayuda.getId());
        } catch (Exception ex) {
            log.error("FAIL Match Ayuda Online: " + ex.getMessage());
        }
    }

    private Map<String, Object> createRequest(final Ayuda ayuda){
        Map<String, Object> request = new HashMap<>();
        request.put("reminderType", "single");
        request.put("ayudaId", ayuda.getId());
        return request;
    }
}
