package domain.topico;

public record ActualizarTopico(
        String titulo,
        String mensaje,
        String nombre_Curso,
        Long usuario_id,
        String status



) {
    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre_Curso() {
        return nombre_Curso;
    }

    @Override
    public String status() {
        return status;
    }

    @Override
    public String titulo() {
        return titulo;
    }

    @Override
    public Long usuario_id() {
        return usuario_id;
    }
}
