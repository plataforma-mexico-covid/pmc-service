package mx.mexicocovid19.plataforma.config.infrastructure;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.config.security.JwtUser;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.model.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Aspect
@Component
@Log4j2
public class UserSessionAop {


    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletResponse response;

    @Pointcut("execution(* mx.mexicocovid19.plataforma.controller.*Controller.*(..))")
    public void controllerLayer() {
    }

    @Around("controllerLayer()")
    public Object aroundControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            log.info("Argumento " + jwtUser);

            for (Object arg : pjp.getArgs()) {
                if (arg instanceof User && jwtUser != null && !jwtUser.getUsername().isEmpty()) {
                    Optional<User> user = userRepository.findById(jwtUser.getUsername());
                    if (user.isPresent()) {
                        ((User) arg).setUsername(user.get().getUsername());
                        ((User) arg).setEnabled(user.get().isEnabled());
                        ((User) arg).setUserRole(user.get().getUserRole());
                    }
                }
            }
        }
        Object retVal = pjp.proceed();
        return retVal;
    }
}
