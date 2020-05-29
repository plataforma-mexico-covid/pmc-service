package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "oferta")
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE", length = 200)
    private String nombre;
    @Column(name = "DESCRIPCION", length = 1000)
    private String descripcion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GEO_LOCATION_ID", nullable = false)
    private GeoLocation ubicacion;
    @Enumerated(EnumType.STRING)
    @Column(name = "RANGO_PRECIO")
    private RangoPrecio rangoPrecio;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS_OFERTA")
    private EstatusAyuda estatusOferta;
    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
    @Column(name = "ACTIVE")
    private Boolean active;
}
