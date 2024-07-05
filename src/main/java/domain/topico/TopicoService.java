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
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidacionTopico> validar;

    public ResponseEntity<RespuestaTopico> registrar(RegistroTopico registroTopico, UriComponentsBuilder uriComponentsBuilder){

        Curso curso = cursoRepository.findByNombreContainsIgnoreCase(registroTopico.getNombre_Curso())
                .orElseThrow(() -> new ValidacionIntegridad("El curso no fue encontrado"));

        Usuario autor = usuarioRepository.findById(registroTopico.getUsuario_id())
                .orElseThrow(() -> new ValidacionIntegridad("El usuario no fue encontrado"));

        validar.forEach(v -> v.validar(registroTopico));

        Topico topico = new Topico(registroTopico, curso, autor);
        Topico topicoRet = topicoRepository.save(topico);

        RespuestaTopico respuestaTopico = new RespuestaTopico(
                topicoRet.getId(),
                topicoRet.getTitulo(),
                topicoRet.getMensaje(),
                topicoRet.getFecha_creacion().toString(),
                topicoRet.getEstado().getStatus(),
                topicoRet.getCurso().getId(),
                topicoRet.getAutor().getId()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRet.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaTopico);
    }

    public ResponseEntity<Page<ListadoTopico>> listarTopicos(Pageable paginacion){
        Page<Topico> topicos = topicoRepository.listadoTopico(paginacion);
        Page<ListadoTopico> listado = topicos.map(ListadoTopico::new);
        return ResponseEntity.ok(listado);
    }

    public ResponseEntity<ListadoRespuestaConTopico> listarDetalleTopicos(Long id){
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionIntegridad("El tópico no fue encontrado. Verifique el id."));

        ListadoRespuestaConTopico datosTopico = new ListadoRespuestaConTopico(topico);
        return ResponseEntity.ok(datosTopico);
    }

    public ResponseEntity<RespuestaTopico> actualizarTopico(ActualizarTopico actualizarTopico,
                                                            Long id, UriComponentsBuilder uriComponentsBuilder) {

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionIntegridad("El tópico no fue encontrado. Verifique el id."));

        Curso curso = null;
        Usuario usuario = null;

        if (actualizarTopico.nombre_Curso() != null) {
            curso = cursoRepository.findByNombreContainsIgnoreCase(actualizarTopico.nombre_Curso())
                    .orElseThrow(() -> new ValidacionIntegridad("El curso no fue encontrado"));
        }

        if (actualizarTopico.usuario_id() != null) {
            usuario = usuarioRepository.findById(actualizarTopico.usuario_id())
                    .orElseThrow(() -> new ValidacionIntegridad("El usuario no fue encontrado"));
        }

        topico.actualizarDatos(actualizarTopico, curso, usuario);
        topicoRepository.save(topico);

        RespuestaTopico respuestaTopico = new RespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion().toString(),
                topico.getEstado().getStatus(),
                topico.getCurso().getId(),
                topico.getAutor().getId()
        );

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

