package mx.mexicocovid19.plataforma.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import mx.mexicocovid19.plataforma.controller.dto.FeedBackDTO;
import mx.mexicocovid19.plataforma.model.entity.FeedBack;
import mx.mexicocovid19.plataforma.util.DateUtil;

public class FeedBackMapper {
	
    public static FeedBackDTO from(final FeedBack feedback) {
        final FeedBackDTO feedbackDTO = new FeedBackDTO();
        
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setCiudadano(CiudadanoMapper.from(feedback.getCiudadano()));
        feedbackDTO.setDescripcion(feedback.getDescripcion());
        feedbackDTO.setFechaCreacion(DateUtil.formatDTO(feedback.getFechaCreacion()));
     
        return feedbackDTO;
    }

    public static FeedBack from(final FeedBackDTO feedbackDTO) {
        final FeedBack feedBack = new FeedBack();
        
        feedBack.setDescripcion(feedbackDTO.getDescripcion());
        feedBack.setCiudadano(CiudadanoMapper.from(feedbackDTO.getCiudadano()));
        feedBack.setFechaCreacion(DateUtil.parseDTO(feedbackDTO.getFechaCreacion()));
        
        return feedBack;
    }

    public static List<FeedBackDTO> from(final List<FeedBack> allfeedback) {
        return allfeedback.stream().map(FeedBackMapper::from).collect(Collectors.toList());
    }

}
