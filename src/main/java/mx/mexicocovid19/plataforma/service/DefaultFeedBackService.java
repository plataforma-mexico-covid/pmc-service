package mx.mexicocovid19.plataforma.service;

import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.OFRECE_AYUDA;
import static mx.mexicocovid19.plataforma.service.TipoEmailEnum.SOLICITA_AYUDA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.model.entity.FeedBack;
import mx.mexicocovid19.plataforma.model.entity.User;
import mx.mexicocovid19.plataforma.model.repository.CiudadanoRepository;
import mx.mexicocovid19.plataforma.model.repository.FeedBackRepository;
import mx.mexicocovid19.plataforma.service.helper.AyudaRateRegisterEvaluationServiceHelper;
import mx.mexicocovid19.plataforma.service.helper.GroseriasHelper;
import mx.mexicocovid19.plataforma.util.ErrorEnum;

@Log4j2
@Service
public class DefaultFeedBackService implements FeedBackService{
	
	@Autowired
	public FeedBackRepository feedbackrepo;
	
    @Autowired
    private AyudaRateRegisterEvaluationServiceHelper ayudaRateRegisterEvaluation;
    
    @Autowired
    private CiudadanoRepository ciudadanoRepository;
    
	
	@Override
	public List<FeedBack> listAllFeedback() {
		return feedbackrepo.findAll();
	}
	
	@Override
	@Transactional
	public FeedBack createFeedBack(FeedBack feedBack, String username) throws PMCException {
	     // Valida el numero de ayudas que ha registrado el usuario firmado
		if ( ayudaRateRegisterEvaluation.isMaximumRequestsPerHourExceeded(username) ) {
			throw new PMCException(ErrorEnum.ERR_MAX_AYUDA, "DefaultAyudaService");
		}
		
		
		User user = new User();
		user.setUsername(username);
		Ciudadano ciudadano = ciudadanoRepository.findByUser(user);
		
		
		if ( !GroseriasHelper.evaluarTexto(feedBack.getDescripcion()) ) {
			
			FeedBack feedbackTmp = feedbackrepo.save(feedBack);

			// TODO Envia notificacion por correo electronic
			

			return feedbackTmp;	
		} else {        		
			throw new PMCException(ErrorEnum.ERR_LENGUAJE_SOEZ, "DefaultAyudaService");	
		}
	}

}
