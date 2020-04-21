package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "VOLUNTARIO")
public class Voluntario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISCIPLINA_ID", nullable = false)
    private Disciplina disciplina;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UBICACION_ID", nullable = false)
    private GeoLocation ubicacion;
    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ACTIVE")
    private Boolean active;
}
