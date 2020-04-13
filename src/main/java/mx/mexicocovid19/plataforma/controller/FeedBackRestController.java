package mx.mexicocovid19.plataforma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.mexicocovid19.plataforma.ApiController;
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil;
import mx.mexicocovid19.plataforma.controller.dto.FeedBackDTO;
import mx.mexicocovid19.plataforma.controller.mapper.FeedBackMapper;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.entity.FeedBack;
import mx.mexicocovid19.plataforma.service.FeedBackService;

@Controller
public class FeedBackRestController {
	
	@Autowired
	private FeedBackService feedBackService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
    @ResponseBody
    @GetMapping(
            value = { ApiController.API_PATH_PRIVATE + "/feedback" },
            produces = {"application/json;charset=UTF-8"})
    public List<FeedBackDTO> readfeedback() {
        return FeedBackMapper.from(feedBackService.listAllFeedback());
    }
	

    @ResponseBody
    @PostMapping(value = { ApiController.API_PATH_PRIVATE + "/feedback" }, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackDTO feedbackDTO, HttpServletRequest request) throws PMCException {
        String token = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        
        ResponseEntity<FeedBackDTO> response = new ResponseEntity<FeedBackDTO>(HttpStatus.BAD_REQUEST);
        
		FeedBack createFeedback = feedBackService.createFeedBack(FeedBackMapper.from(feedbackDTO), username);
		response = new ResponseEntity<FeedBackDTO>(FeedBackMapper.from(createFeedback), HttpStatus.OK);
        
        return response;
    }
    
   
	@ResponseBody
	@DeleteMapping(value = { ApiController.API_PATH_PRIVATE + "/feedback/{id}" }, produces = {
			"application/json;charset=UTF-8" })
	public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {

		try {
			feedBackService.eliminar(id);
			return ResponseEntity.noContent().build();

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
    
	
}
