package mx.mexicocovid19.plataforma.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import mx.mexicocovid19.plataforma.controller.dto.FeedBackDTO;
import mx.mexicocovid19.plataforma.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.model.entity.FeedBack;
import mx.mexicocovid19.plataforma.model.entity.Peticion;
import mx.mexicocovid19.plataforma.util.DateUtil;

public class FeedBackMapper {
	
    public static FeedBackDTO from(final FeedBack feedback) {
        final FeedBackDTO feedbackDTO = new FeedBackDTO();
        
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setCiudadano_id(feedback.getCiudadano().getId());
        feedbackDTO.setPeticion_id(feedback.getPeticion().getId());
        feedbackDTO.setMensaje(feedback.getDescripcion());
        feedbackDTO.setFechaCreacion(DateUtil.formatDTO(feedback.getFechaCreacion()));
     
        return feedbackDTO;
    }

    public static FeedBack from(final FeedBackDTO feedbackDTO) {
        final FeedBack feedBack = new FeedBack();
        
        feedBack.setDescripcion(feedbackDTO.getMensaje());
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setId(feedbackDTO.getCiudadano_id());
        feedBack.setCiudadano(ciudadano);
        Peticion peticion = new Peticion();
        peticion.setId(feedbackDTO.getPeticion_id());
        feedBack.setPeticion(peticion);
        feedBack.setFechaCreacion(DateUtil.parseDTO(feedbackDTO.getFechaCreacion()));
        
        return feedBack;
    }

    public static List<FeedBackDTO> from(final List<FeedBack> allfeedback) {
        return allfeedback.stream().map(FeedBackMapper::from).collect(Collectors.toList());
    }

}
