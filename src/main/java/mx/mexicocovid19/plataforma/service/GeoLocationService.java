package mx.mexicocovid19.plataforma.service;

import java.util.Map;

public interface GeoLocationService {
    Map<String, Double> getPositionByPostalCode(final String postalCode);
}
