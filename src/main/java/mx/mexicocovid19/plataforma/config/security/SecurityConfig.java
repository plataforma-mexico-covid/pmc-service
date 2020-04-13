package mx.mexicocovid19.plataforma.config.security;

import mx.mexicocovid19.plataforma.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by betuzo on 25/01/15.
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.codigoartesanal.entuliga.config.security" })

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, ApiController.API_PATH_PUBLIC + "/**").permitAll()
                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .antMatchers(HttpMethod.PUT, ApiController.API_PATH_PUBLIC + "/**").permitAll()
                .antMatchers(HttpMethod.POST, ApiController.API_PATH_PUBLIC + "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, ApiController.API_PATH_PUBLIC + "/**").permitAll()
                .antMatchers(HttpMethod.POST, ApiController.API_PATH_PRIVATE + "/**").authenticated()
                .antMatchers(HttpMethod.PUT, ApiController.API_PATH_PRIVATE + "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, ApiController.API_PATH_PRIVATE + "/**").authenticated()
                .antMatchers(HttpMethod.POST, ApiController.API_PATH_PRIVATE + "/ayuda_ciudadano").access("hasRole('VOLUNTARY')")
                .antMatchers("/h2-console/**").permitAll()
                .and()
            .csrf()
                .disable()
                //.csrf().ignoringAntMatchers("/h2-console/**")
                //.and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
            .httpBasic();

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http
            .headers()
                .cacheControl()
                .and()
                .frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
