package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "peticion")
public class Peticion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AYUDA_ID", nullable = false)
    private Ayuda ayuda;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AYUDA_MATCH_ID", nullable = true)
    private Ayuda ayudaMatch;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MATCH")
    private TipoMatch tipoMatch;
    @Column(name = "FECHA_PETICION")
    private LocalDateTime fechaPeticion;
}