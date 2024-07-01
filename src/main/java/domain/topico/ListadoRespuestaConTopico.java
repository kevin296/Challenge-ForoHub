package domain.topico;

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
    public void ListadoTopicoConRespuestas(Topico topico){
        this(topico.getId, topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),
                topico.getEstado().toString(), topico.getCurso().getNombre(), topico.getAutor().getNombre(),
                topico.getRespuestas().stream()
                        .map(r -> new ListadoRespuestaConTopico(r))
                        .collect(Collectors.toList()));
    }
}
