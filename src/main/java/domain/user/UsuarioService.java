package domain.user;

import Validaciones.ValidacionUsuario.ValidacionUsuario;
import infra.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidacionUsuario> validacion;

    public ResponseEntity<RespuestaUsuario> registrarUsuario(RegistroUsuario registroUsuario,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        var usuario = new Usuario(registroUsuario);
        validacion.forEach(v -> v.validar(registroUsuario));
        Usuario usuarioConId = usuarioRepository.save(usuario);

        RespuestaUsuario respuestaUsuario = new RespuestaUsuario(usuarioConId);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioConId.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaUsuario);
    }

    public ResponseEntity<RespuestaUsuario> actualizarUsuario(ActualizarUsuario actualizarUsuario, Long id, UriComponentsBuilder uriComponentsBuilder) {

        if (usuarioRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("El usuario no fue encontrado. Verifique el id.");
        }

        Usuario usuario = usuarioRepository.getReferenceById(id);

        RegistroUsuario registroUsuario = realizarCopiaActualizado(usuario, actualizarUsuario);

        validacion.forEach(v -> v.validar(registroUsuario));

        if (actualizarUsuario.nombre() != null){
            usuario.setNombre(actualizarUsuario.nombre());
        }
        if (actualizarUsuario.email() != null){
            usuario.setEmail(actualizarUsuario.email());
        }
        if (actualizarUsuario.clave() != null){
            usuario.setClave(actualizarUsuario.clave());
        }
        if (actualizarUsuario.perfil() != null){
            usuario.setPerfil(Perfiles.fromString(actualizarUsuario.perfil()));
        }

        RespuestaUsuario respuestaUsuario = new RespuestaUsuario(usuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaUsuario);

    }

    public ResponseEntity<Page> listarUsuarios(Pageable paginacion) {

        return ResponseEntity.ok(usuarioRepository.listarUsuarios(paginacion)
                .map(RespuestaUsuario::new));
    }

    public ResponseEntity eliminarUsuario(Long id) {

        if (usuarioRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("El usuario no fue encontrado. Verifique el id.");
        }

        usuarioRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private RegistroUsuario realizarCopiaActualizado(Usuario usuario, ActualizarUsuario actualizarUsuario){
        String nombre = usuario.getNombre();
        String email = usuario.getEmail();
        String clave = usuario.getClave();
        String perfil = usuario.getPerfil().toString();

        if (actualizarUsuario.nombre() != null){
            nombre = actualizarUsuario.nombre();
        }
        if (actualizarUsuario.email() != null){
            email = actualizarUsuario.email();
        }
        if (actualizarUsuario.clave() != null){
            clave = actualizarUsuario.clave();
        }
        if (actualizarUsuario.perfil() != null){
            perfil = actualizarUsuario.perfil();
        }

        RegistroUsuario registroUsuario = new RegistroUsuario(nombre, email, clave, perfil);
        return registroUsuario;
    }


}
