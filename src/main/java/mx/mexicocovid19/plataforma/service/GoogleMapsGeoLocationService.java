package mx.mexicocovid19.plataforma.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Profile("locationon")
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
                Map<String, Double> loc = (Map<String, Double>) ((Map)((Map) ((List) location.get("results")).get(0)).get("geometry")).get("location");
                return moveSomeMeters(loc);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return result;
    }

    private Map<String, Double> moveSomeMeters(final Map<String, Double> location){
        //Position, decimal degrees
        double lat = location.get("lat");
        double lon = location.get("lng");

        //Earth’s radius, sphere
        double R=6378137;

        //offsets in meters
        double dn = randomMeters();
        double de = randomMeters();

        //Coordinate offsets in radians
        double dLat = dn/R;
        double dLon = de/(R*Math.cos(Math.PI*lat/180));

        //OffsetPosition, decimal degrees
        double latO = lat + dLat * 180/Math.PI;
        double lonO = lon + dLon * 180/Math.PI;
        return new HashMap<String, Double>() {{
            put("lat", latO);
            put("lng", lonO);
        }};
    }

    private int randomMeters(){
        Random r = new Random();
        int low = 10;
        int high = 200;
        return r.nextInt(high-low) + low;
    }
}
