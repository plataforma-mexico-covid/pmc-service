package mx.mexicocovid19.plataforma.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.mexicocovid19.plataforma.model.entity.FeedBack;;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer>{
	
	

}
