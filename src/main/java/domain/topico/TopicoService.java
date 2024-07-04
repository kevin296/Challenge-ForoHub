package domain.topico;

import Validaciones.ValidacionTopico.ValidacionTopico;
import domain.curso.Curso;
import domain.curso.CursoRepository;
import domain.user.Usuario;
import domain.user.UsuarioRepository;
import infra.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidacionTopico> validar;

    public ResponseEntity<RespuestaTopico> registrar(RegistroTopico registroTopico, UriComponentsBuilder uriComponentsBuilder){

        if (cursoRepository.findByNombreContainsIgnoreCase(registroTopico.nombre_Curso()).isEmpty()){
            throw new ValidacionIntegridad("El curso no fue encontrado");
        }

        if (usuarioRepository.findById(registroTopico.usuario_id()).isEmpty()){
            throw new ValidacionIntegridad("El usuario no fue encontrado");
        }

        validar.forEach(v -> v.validar(registroTopico));

        var topico = new Topico(registroTopico);

        topico.setCurso(cursoRepository.findByNombreContainsIgnoreCase(registroTopico.nombre_Curso()).get());
        topico.setAutor(usuarioRepository.findById(registroTopico.usuario_id()).get());
        Topico topicoRet = topicoRepository.save(topico);

        RespuestaTopico datosRespuestaTopico = new RespuestaTopico(topicoRet.getId(), topicoRet.getTitulo(),
                topicoRet.getMensaje(), topicoRet.getFecha_creacion().toString(), topicoRet.getEstado().toString(),
                topicoRet.getCurso().getId(), topicoRet.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRet.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    public ResponseEntity<Page> listarTopicos(Pageable paginacion){

        return ResponseEntity.ok(topicoRepository.listadoTopico(paginacion)
                .map(ListadoRespuestaConTopico::new));
    }

    public ResponseEntity<ListadoRespuestaConTopico> listarDetalleTopicos(Long id){

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new ListadoRespuestaConTopico(topico);

        return ResponseEntity.ok(datosTopico);
    }


    public ResponseEntity<RespuestaTopico> actualizarTopico(ActualizarTopico actualizarTopico,
                                                            Long id, UriComponentsBuilder uriComponentsBuilder) {

        Curso curso = null;
        Usuario usuario = null;

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        if (actualizarTopico.nombre_Curso() != null) {
            if (cursoRepository.findByNombreContainsIgnoreCase(actualizarTopico.nombre_Curso()).isEmpty()) {
                throw new ValidacionIntegridad("El curso no fue encontrado");
            }
            curso = cursoRepository.findByNombreContainsIgnoreCase(actualizarTopico.nombre_Curso()).get();
        }

        if (actualizarTopico.usuario_id() != null) {
            if (usuarioRepository.findById(actualizarTopico.usuario_id()).isEmpty()) {
                throw new ValidacionIntegridad("El usuario no fue encontrado");
            }
            usuario = usuarioRepository.findById(actualizarTopico.usuario_id()).get();
        }

        Topico topico = topicoRepository.getReferenceById(id);

        RegistroTopico registroTopico = new RegistroTopico(actualizarTopico.titulo(),
                actualizarTopico.mensaje(), actualizarTopico.nombre_Curso(),
                actualizarTopico.usuario_id());

        validar.forEach(v -> v.validar(registroTopico));

        topico.actualizarDatos(actualizarTopico, curso, usuario);

        RespuestaTopico respuestaTopico = new RespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFecha_creacion().toString(), topico.getEstado().toString(),
                topico.getCurso().getId(), topico.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaTopico);

    }


    public ResponseEntity eliminarTopico(Long id) {

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        topicoRepository.borrarTopico(id);

        return ResponseEntity.noContent().build();
    }

}
