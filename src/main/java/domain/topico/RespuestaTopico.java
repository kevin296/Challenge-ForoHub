package domain.topico;

public record RespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fecha_creacion,
        String estado,
        Long curso_id,
        Long usuario_id


) {


}
