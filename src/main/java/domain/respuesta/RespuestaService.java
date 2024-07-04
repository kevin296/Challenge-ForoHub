package domain.respuesta;

import domain.topico.ListadoTopico;
import domain.topico.Topico;
import domain.topico.TopicoRepository;
import domain.user.Usuario;
import domain.user.UsuarioRepository;
import infra.ValidacionIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ResponseEntity<RetornoRespuesta> registroRespuesta(RegistroRespuesta registroRespuesta,
                                                              UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.findById(registroRespuesta.topico_id()).isEmpty()){
            throw new ValidacionIntegridad("El tópico de la respuesta no fue encontrado. Revise el id.");
        }

        if (usuarioRepository.findById(registroRespuesta.usuario_id()).isEmpty()){
            throw new ValidacionIntegridad("El autor de la respuesta no fue encontrado. Revise el id.");
        }

        Topico topico = topicoRepository.getReferenceById(registroRespuesta.topico_id());
        Usuario autor = usuarioRepository.getReferenceById(registroRespuesta.usuario_id());
        Respuesta respuesta = new Respuesta(registroRespuesta, topico, autor);
        Respuesta respuestaRet = respuestaRepository.save(respuesta);

        RetornoRespuesta retornoRespuesta = new RetornoRespuesta(respuestaRet.getId(), respuestaRet.getMensaje(),
                respuestaRet.getFecha_creacion(), new ListadoTopico(respuestaRet.getTopico()),
                respuestaRet.getAutor().getNombre(), respuestaRet.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaRet.getId()).toUri();

        return ResponseEntity.created(url).body(retornoRespuesta);

    }

    public ResponseEntity<RetornoRespuesta> actualizarRespuesta(ActualizarRespuesta actualizarRespuesta,
                                                                     Long id, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = null;
        Usuario usuario = null;
        if (respuestaRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("La respuesta no fue encontrada. Verifique el id.");
        }

        if (actualizarRespuesta.topico_id() != null){
            if (topicoRepository.findById(actualizarRespuesta.topico_id()).isEmpty()){
                throw new ValidacionIntegridad("El tópico de la respuesta no fue encontrado. Verifique el id.");
            }
            topico = topicoRepository.findById(actualizarRespuesta.topico_id()).get();
        }

        if (actualizarRespuesta.usuario_id() != null){
            if (usuarioRepository.findById(actualizarRespuesta.usuario_id()).isEmpty()){
                throw new ValidacionIntegridad("El usuario de la respuesta no fue encontrado. Verifique el id.");
            }
            usuario = usuarioRepository.findById(actualizarRespuesta.usuario_id()).get();
        }

        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarDatos(actualizarRespuesta, topico, usuario);
        RetornoRespuesta retornoRespuesta = new RetornoRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getFecha_creacion(), new ListadoTopico(respuesta.getTopico()), respuesta.getAutor().getNombre(), respuesta.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(url).body(retornoRespuesta);
    }

    public ResponseEntity<Page> listarRespuestas(Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.listarRespuestas(paginacion)
                .map(ListadoRespuesta::new));
    }


    public ResponseEntity eliminarRespuesta(Long id) {

        if (respuestaRepository.findById(id).isEmpty()){
            throw new ValidacionIntegridad("La respuesta no fue encontrada. Verifique el id.");
        }

        respuestaRepository.borrarRespuesta(id);

        return ResponseEntity.noContent().build();
    }




}
