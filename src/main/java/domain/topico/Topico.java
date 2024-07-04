package domain.topico;


import domain.curso.Curso;
import domain.respuesta.Respuesta;
import domain.user.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Table(name = "topico")
 @Entity(name="Topico")
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
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario autor;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<Respuesta> respuestas;

    public Topico(RegistroTopico registroTopico) {
        this.titulo = registroTopico.titulo();
        this.mensaje = registroTopico.mensaje();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horaFormateada = ahora.format(formateador);
        this.fecha_creacion = LocalDateTime.parse(horaFormateada, formateador);

        this.estado = Estado.SIN_SOLUCION;

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
    // Getters y setters
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

    public void setEstado(Estado estado) {
        this.estado = estado;
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
