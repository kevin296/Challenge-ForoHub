package domain.respuesta;

public record ListadoRespuestaTopico(
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        String autor,
        Boolean solucion



) {
    public DatosListadoRespuestaEnTopico(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }


}
