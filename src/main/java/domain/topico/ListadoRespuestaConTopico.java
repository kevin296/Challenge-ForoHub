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
    // Constructor personalizado que toma un objeto Topico o Respuesta
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
                        .map(ListadoRespuestaConTopico::new) // Assuming ListadoRespuestaConTopico can handle Topico
                        .collect(Collectors.toList())
        );
    }



    public ListadoRespuestaConTopico(Respuesta respuesta) {
        this(
                null, // id (assuming it's not directly available from Respuesta)
                respuesta.getTopico().getTitulo(), // use the topico's title
                respuesta.getMensaje(),
                respuesta.getFecha_creacion(),
                respuesta.getTopico().getEstado().toString(),
                respuesta.getTopico().getCurso().getNombre(),
                respuesta.getAutor().getNombre(),
                null
        );
}
}