package mx.mexicocovid19.plataforma.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("locationoff")
public class MockGeoLocationService implements GeoLocationService {

    @Override
    public Map<String, Double> getPositionByPostalCode(String postalCode) {
        Map<String, Double> location = new HashMap<>();
        location.put("lat", 11.000222);
        location.put("lng", -99.99882);
        return location;
    }
}
