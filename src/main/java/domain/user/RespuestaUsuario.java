package domain.user;

public record RespuestaUsuario(
        Long id,
        String nombre,
        String email,
        String perfil
) {
    public RespuestaUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getPerfil().toString());
    }
}
