package domain.respuesta;

public record ActualizarRespuesta(
        String mensaje,
        Long topico_id,
        Long usuario_id,
        Boolean solucion

) {

}

