package mx.mexicocovid19.plataforma.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
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
    public void verifyMatchAutomatic(final Ayuda ayuda) {
        log.info("verifyMatchAutomatic: " + this.analizerUrl + " ayuda: " + ayuda.getId());
        try{
            Map<String, Object> request = createRequestMatchAutomatic(ayuda);
            ResponseEntity<Void> response
                    = restTemplate.postForEntity(this.analizerUrl + "/api/v1/internal/analizer/reminder", request, Void.class);
            log.info("INFO Match Ayuda Online: " + ayuda.getId() + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
            if (response.getStatusCode() == HttpStatus.CREATED)
                return;
            log.error("FAIL Match Ayuda Online: " + ayuda.getId() + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
        } catch (Exception ex) {
            log.error("FAIL Match Ayuda Online: " + ex.getMessage());
        }
    }

    @Override
    public void verifyMatchManual(Ayuda ayuda, Ciudadano ciudadano) {
        log.info("verifyMatchManual: " + this.analizerUrl + " ayuda: " + ayuda.getId() + " ciudadano: " + ciudadano.getId());
        try{
            Map<String, Object> request = createRequestMatchManual(ayuda, ciudadano);
            ResponseEntity<Void> response
                    = restTemplate.postForEntity(this.analizerUrl + "/api/v1/internal/notification/match", request, Void.class);
            log.info("INFO Match Ayuda Online: " + ayuda.getId() + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
            if (response.getStatusCode() == HttpStatus.CREATED)
                return;
            log.error("FAIL Match Ayuda Online: " + ayuda.getId() + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
        } catch (Exception ex) {
            log.error("FAIL Match Ayuda Online: " + ex.getMessage());
        }
    }

    @Override
    public void sendMessage(String numero, String message) {
        log.info("sendMessage: " + this.analizerUrl + " numero: " + numero + " message: " + message);
        try{
            Map<String, Object> request = createRequestSendMessage(numero, message);
            ResponseEntity<Void> response
                    = restTemplate.postForEntity(this.analizerUrl + "/api/v1/internal/notification/send", request, Void.class);
            log.info("INFO sendMessage: " + numero + " message: " + message + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
            if (response.getStatusCode() == HttpStatus.CREATED)
                return;
            log.info("FAIL sendMessage: " + numero + " message: " + message + " status: " + response.getStatusCode() + " request: " + new Gson().toJson(request));
        } catch (Exception ex) {
            log.error("FAIL Match Ayuda Online: " + ex.getMessage());
        }
    }

    private Map<String, Object> createRequestSendMessage(final String numero, final String message){
        Map<String, Object> request = new HashMap<>();
        request.put("numero", numero);
        request.put("message", message);
        return request;
    }

    private Map<String, Object> createRequestMatchManual(final Ayuda ayuda, final Ciudadano ciudadano){
        Map<String, Object> request = new HashMap<>();
        request.put("ayudaId", ayuda.getId());
        request.put("ciudadanoId", ciudadano.getId());
        return request;
    }

    private Map<String, Object> createRequestMatchAutomatic(final Ayuda ayuda){
        Map<String, Object> request = new HashMap<>();
        request.put("reminderType", "single");
        request.put("ayudaId", ayuda.getId());
        return request;
    }
}
