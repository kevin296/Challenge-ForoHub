package domain.topico;

import java.time.LocalDateTime;


public record ListadoTopico(Long id,
                            String titulo,
                            String mensaje,
                            LocalDateTime fecha_creacion,
                            String estado,
                            String curso,
                            String autor) {


     public ListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),
                topico.getEstado().toString(), topico.getCurso().getNombre(), topico.getAutor().getNombre());
    }

}
