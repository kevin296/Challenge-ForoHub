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
    Usuario autor;

    private Boolean solucion;

    public Respuesta(RegistroRespuesta registroRespuesta, Topico topico, Usuario autor) {
        this.mensaje = registroRespuesta.mensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.topico = topico;
        this.autor = autor;
        this.solucion = false;

    }
    public void actualizarDatos(ActualizarRespuesta actualizarRespuesta, Topico topico, Usuario autor) {
        if (actualizarRespuesta.mensaje() != null){
            this.mensaje = actualizarRespuesta.mensaje();
        }
        if (topico != null){
            this.topico = topico;
        }
        if (autor != null){
            this.autor = autor;
        }
        if (actualizarRespuesta.solucion() != null){
            if (actualizarRespuesta.solucion()){
                this.solucion = true;
                this.topico.setEstado(Estado.SOLUCIONADO);
            }else{
                this.solucion = false;
            }
        }
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
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

    public void setFecha_creacion(LocalDateTime fechaCreacion) {
        this.fecha_creacion = fechaCreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }
}
