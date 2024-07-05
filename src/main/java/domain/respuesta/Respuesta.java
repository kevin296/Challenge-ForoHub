package domain.respuesta;

import domain.topico.Estado;
import domain.topico.Topico;
import domain.user.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fecha_creacion;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    private Boolean solucion;

    public Respuesta(RegistroRespuesta registroRespuesta, Topico topico, Usuario autor) {
        this.mensaje = registroRespuesta.mensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.topico = topico;
        this.autor = autor;
        this.solucion = false;
    }

    public void actualizarDatos(ActualizarRespuesta actualizarRespuesta, Topico topico, Usuario autor) {
        if (actualizarRespuesta.mensaje() != null) {
            this.mensaje = actualizarRespuesta.mensaje();
        }
        if (topico != null) {
            this.topico = topico;
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (actualizarRespuesta.solucion() != null) {
            this.solucion = actualizarRespuesta.solucion();
            if (this.solucion) {
                this.topico.setEstado(Estado.SOLUCIONADO); // Corregido: Utiliza setEstado para establecer el estado
            } else {
                this.topico.setEstado(Estado.SIN_SOLUCION); // Corregido: Utiliza setEstado para establecer el estado
            }
        }
    }
}
