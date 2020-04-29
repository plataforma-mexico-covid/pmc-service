package mx.mexicocovid19.plataforma.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("locationoff")
public class MockGeoLocationService implements GeoLocationService {

    @Override
    public Map<String, Double> getPositionByPostalCode(String postalCode) {
        return null;
    }
}
