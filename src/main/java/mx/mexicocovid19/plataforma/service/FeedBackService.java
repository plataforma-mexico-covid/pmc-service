package mx.mexicocovid19.plataforma.service;

import java.util.List;

import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.FeedBack;

public interface FeedBackService {
	
	public List<FeedBack> listAllFeedback();
	FeedBack createFeedBack(final FeedBack feedBack, final String username) throws PMCException;

}
