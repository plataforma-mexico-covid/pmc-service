package mx.mexicocovid19.plataforma.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class FeedBack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CIUDADANO_ID", nullable = false)
	private Ciudadano ciudadano;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PETICION_ID", nullable = false)
	private Peticion peticion;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
	

}
