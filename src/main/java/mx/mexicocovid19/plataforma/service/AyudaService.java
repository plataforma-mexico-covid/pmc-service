package mx.mexicocovid19.plataforma.service;

import java.util.List;

import javax.mail.MessagingException;

import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import org.springframework.security.core.GrantedAuthority;

public interface AyudaService {
    List<Ayuda> readAyudas(final String origenAyuda, final Double longitude, final Double latitude, final Integer kilometers);
    List<Ayuda> readAyudasByEstatusAyuda(String estatus);
    List<Ayuda> readAyudas();
    Ayuda createAyuda(final Ayuda ayuda, final String username) throws PMCException;
    Ayuda createAyudaAndCiudadano(final Ayuda ayuda) throws PMCException;
    void matchAyuda(final Integer idAyuda, final String username) throws MessagingException;
    String getOrigenByRole(final List<GrantedAuthority> roles, final String origen);
}
