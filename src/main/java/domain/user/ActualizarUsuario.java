package domain.user;

public record ActualizarUsuario (
        String nombre,
        String email,
        String clave,
        String perfil
) {
}
