package domain.user;

public record RegistroUsuario(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @Email
        @NotNull(message = "Email es obligatorio")
        String email,
        @NotBlank(message = "Clave es obligatorio")
        String clave,
        @NotBlank(message = "Perfil es obligatorio")
        String perfil

) {
    public RegistroUsuario(String nombre, String email, String clave, String perfil) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.perfil = perfil;
    }

}
