package domain.respuesta;

import java.time.LocalDateTime;

public record ListadoRespuestaTopico(
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        String autor,
        Boolean solucion



) {
    public ListadoRespuestaTopico(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }


}
