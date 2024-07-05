package domain.topico;


import domain.respuesta.Respuesta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ListadoRespuestaConTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        String estado,
        String curso,
        String autor,
        List<ListadoRespuestaConTopico> respuestas
) {

    public ListadoRespuestaConTopico(Topico topico) {

        this(

                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getEstado().toString(),
                topico.getCurso().getNombre(),
                topico.getAutor().getNombre(),
                topico.getRespuestas().stream()
                        .map(ListadoRespuestaConTopico::new) 
                        .collect(Collectors.toList())
        );
    }



    public ListadoRespuestaConTopico(Respuesta respuesta) {
        this(
                null, 
                respuesta.getTopico().getTitulo(), 
                respuesta.getMensaje(),
                respuesta.getFecha_creacion(),
                respuesta.getTopico().getEstado().toString(),
                respuesta.getTopico().getCurso().getNombre(),
                respuesta.getAutor().getNombre(),
                null
        );
}
}