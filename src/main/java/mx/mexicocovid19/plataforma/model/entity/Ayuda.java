package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ayuda")
public class Ayuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GEO_LOCATION_ID", nullable = false)
    private GeoLocation ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TIPO_AYUDA_ID", nullable = false)
    private TipoAyuda tipoAyuda;
    @Enumerated(EnumType.STRING)
    @Column(name = "ORIGEN_AYUDA")
    private OrigenAyuda origenAyuda;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS_AYUDA")
    private EstatusAyuda estatusAyuda;
    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
    @Column(name = "ACTIVE")
    private Boolean active;
}