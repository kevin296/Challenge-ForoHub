package domain.respuesta;

import domain.topico.ListadoTopico;

import java.time.LocalDateTime;

public record ListadoRespuesta(Long id,
                               String mensaje,
                               LocalDateTime fecha_creacion,
                               String autor,
                               Boolean solucion,
                               ListadoTopico topico) {


    public ListadoRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion(),
                new DatosListadoTopico(respuesta.getTopico()));
    }


}



