package mx.mexicocovid19.plataforma.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.model.entity.UserRole;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User usuario) {
        return new JwtUser(
                usuario.getUsername(),
                usuario.getUsername(),
                usuario.getPassword(),
                mapToGrantedAuthorities(usuario.getUserRole()),
                true
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(Set<UserRole> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().toString()))
                .collect(Collectors.toList());
    }

}
