package domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroTopico( @NotBlank(message = "Titulo debe de estar lleno")
                              String titulo,
                              @NotBlank(message = "Mensaje debe de estar lleno")
                              String mensaje,
                              @NotBlank(message = "mesaje debe de estar lleno")
                              String nombre_Curso,
                              @NotNull(message ="usuario_id debe de estar lleno ")
                              Long usuario_id) {

    public RegistroTopico(String titulo, String mensaje, String nombre_Curso, Long usuario_id){
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.nombre_Curso = nombre_Curso;
        this.usuario_id = usuario_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getNombre_Curso() {
        return nombre_Curso;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }



}
