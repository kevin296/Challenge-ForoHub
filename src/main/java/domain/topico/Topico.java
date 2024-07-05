package domain.topico;


import domain.curso.Curso;
import domain.respuesta.Respuesta;
import domain.user.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "topico")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of= "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fecha_creacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas;

    public Topico(RegistroTopico registroTopico, Curso curso, Usuario autor) {

        this.titulo = registroTopico.getTitulo();
        this.mensaje = registroTopico.getMensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.estado = Estado.SIN_SOLUCION;
        this.curso = curso;
        this.autor = autor;
    }

    public void actualizarDatos(ActualizarTopico actualizarTopico, Curso curso, Usuario autor){
        if (actualizarTopico.titulo() != null) {
            this.titulo = actualizarTopico.titulo();
        }
        if (actualizarTopico.mensaje() != null){
            this.mensaje = actualizarTopico.mensaje();
        }
        if (curso != null){
            this.curso = curso;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (actualizarTopico.status() != null){
            this.estado = Estado.fromString(actualizarTopico.status());
        }
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
