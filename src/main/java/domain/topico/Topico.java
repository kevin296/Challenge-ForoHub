package domain.topico;


import domain.curso.Curso;
import domain.respuesta.Respuesta;
import domain.user.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

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
    private long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha_creacion;
    @Enumerated(EnumType.STRING)
    private Estado status;
    @ManyToOne
    @JoinColumn(name= "curso_id")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name= "usuario_id")
    Usuario autor;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<Respuesta> respuesta;

    public Topico(RegistroTopico registroTopico){
        this.titulo = registroTopico.titulo();
        this.mensaje = registroTopico.mensaje();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horanueva = ahora.format(formateador);
        this.fecha_creacion = LocalDateTime.parse(horanueva, formateador);

        this.status = Estado.SIN_SOLUCION;
    }



    public void actualizarDatos(ActualizarTopico ActualizarTopico) {
        if (ActualizarTopico.titulo() != null) {
            this.titulo = ActualizarTopico.titulo();
        }
        if (ActualizarTopico.mensaje() != null) {
            this.mensaje = ActualizarTopico.mensaje();
        }
        if (curso != null){
            this.curso = curso;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (ActualizarTopico.status() != null){
            this.status = Estado.fromString(ActualizarTopico.status());
        }
    }

}
