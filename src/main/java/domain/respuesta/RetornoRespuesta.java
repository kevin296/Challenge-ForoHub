package domain.respuesta;

import domain.topico.ListadoTopico;

import java.time.LocalDateTime;

public record RetornoRespuesta(

        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        ListadoTopico topico,
        String autor,
        Boolean solucion
) {




}
