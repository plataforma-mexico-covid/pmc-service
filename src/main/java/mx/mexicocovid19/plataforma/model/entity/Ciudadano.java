package mx.mexicocovid19.plataforma.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ciudadano")
public class Ciudadano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "PATERNO")
    private String paterno;
    @Column(name = "MATERNO")
    private String materno;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERNAME", nullable = true)
    private User user;
    @Column(name = "ACTIVE")
    private Boolean active;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ciudadano")
    private Set<CiudadanoContacto> contactos = new HashSet<CiudadanoContacto>(0);

    public String getNombreCompleto() {
        return this.nombre + " " + this.paterno + " " + this.materno;
    }
}