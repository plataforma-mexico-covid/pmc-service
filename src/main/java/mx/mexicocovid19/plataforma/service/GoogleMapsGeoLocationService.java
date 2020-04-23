package mx.mexicocovid19.plataforma.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleMapsGeoLocationService implements GeoLocationService {

    @Value("${plataforma.google.apiKey}")
    private String apiKey;

    RestTemplate restTemplate = new RestTemplate();

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Map<String, Double> getPositionByPostalCode(String postalCode) {
        Map<String, Double> result = new HashMap<>();
        try{
            String pc = String.format("%1$" + 5 + "s", postalCode).replace(' ', '0');
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=CodigoPostal" + pc + "&key=" + this.apiKey;
            ResponseEntity<Map> response
                    = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() != HttpStatus.OK)
                return result;
            Map location = response.getBody();
            if (location.get("status").equals("OK")){
                return (Map<String, Double>) ((Map)((Map) ((List) location.get("results")).get(0)).get("geometry")).get("location");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return result;
    }
}
