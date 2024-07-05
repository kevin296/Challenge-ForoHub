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
        if (actualizarTopico.getTitulo() != null) {
            this.titulo = actualizarTopico.getTitulo();
        }
        if (actualizarTopico.getMensaje() != null){
            this.mensaje = actualizarTopico.getMensaje();
        }
        if (curso != null){
            this.curso = curso;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (actualizarTopico.getStatus != null){
            this.estado = Estado.fromString(actualizarTopico.getStatus());
        }
    }

    // Método para obtener el estado actual
    public Estado getEstado() {
        return estado;
    }
}
