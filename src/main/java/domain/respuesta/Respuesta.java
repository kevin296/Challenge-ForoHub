package domain.respuesta;


import domain.topico.Estado;
import domain.topico.Topico;
import domain.user.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @JoinColumn(name="topico_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario autor;
    private Boolean solucion;

    public Respuesta(RegistroRespuesta datosRegistroRespuesta, Topico topico, Usuario autor) {
        this.mensaje = datosRegistroRespuesta.mensaje();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horaFormateada = ahora.format(formateador);
        this.fecha_creacion = LocalDateTime.parse(horaFormateada, formateador);

        this.topico = topico;
        this.autor = autor;
        this.solucion = false;


    }
    public void actualizarDatos(ActualizarRespuesta datosActualizarRespuesta, Topico topico, Usuario autor) {
        if (datosActualizarRespuesta.mensaje() != null){
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
        if (topico != null){
            this.topico = topico;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (datosActualizarRespuesta.solucion() != null){
            if (datosActualizarRespuesta.solucion()){
                this.solucion = true;
                this.topico.setEstado(Estado.SOLUCIONADO);
            }else{
                this.solucion = false;
            }
        }
    }


}
