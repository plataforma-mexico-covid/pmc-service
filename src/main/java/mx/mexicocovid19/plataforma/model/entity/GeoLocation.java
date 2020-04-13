package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "geo_location")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "NO_EXTERIOR")
    private String noExterior;
    @Column(name = "NO_INTERIOR")
    private String noInterior;
    @Column(name = "COLONIA")
    private String colonia;
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MUNICIPALITY_ID", nullable = true)
    private Municipality municipality;
    @Column(name = "LONGITUDE", precision=12, scale=8)
    private Double longitude;
    @Column(name = "LATITUDE", precision=12, scale=8)
    private Double latitude;
}
