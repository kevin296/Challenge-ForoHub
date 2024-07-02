package domain.respuesta;

import domain.topico.TopicoRepository;
import domain.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ResponseEntity<DatosRetornoRespuesta> registrarRespuesta(DatosRegistroRespuesta datosRegistroRespuesta,
                                                                    UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.findById(datosRegistroRespuesta.topico_id()).isEmpty()){
            throw new ValidacionDeIntegridad("El tópico de la respuesta no fue encontrado. Revise el id.");
        }

        if (usuarioRepository.findById(datosRegistroRespuesta.usuario_id()).isEmpty()){
            throw new ValidacionDeIntegridad("El autor de la respuesta no fue encontrado. Revise el id.");
        }

        Topico topico = topicoRepository.getReferenceById(datosRegistroRespuesta.topico_id());
        Usuario autor = usuarioRepository.getReferenceById(datosRegistroRespuesta.usuario_id());
        Respuesta respuesta = new Respuesta(datosRegistroRespuesta, topico, autor);
        Respuesta respuestaRet = respuestaRepository.save(respuesta);

        DatosRetornoRespuesta datosRetornoRespuesta = new DatosRetornoRespuesta(respuestaRet.getId(), respuestaRet.getMensaje(),
                respuestaRet.getFecha_creacion(), new DatosListadoTopico(respuestaRet.getTopico()),
                respuestaRet.getAutor().getNombre(), respuestaRet.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaRet.getId()).toUri();

        return ResponseEntity.created(url).body(datosRetornoRespuesta);

    }

    public ResponseEntity<DatosRetornoRespuesta> actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta,
                                                                     Long id, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = null;
        Usuario usuario = null;
        if (respuestaRepository.findById(id).isEmpty()){
            throw new ValidacionDeIntegridad("La respuesta no fue encontrada. Verifique el id.");
        }

        if (datosActualizarRespuesta.topico_id() != null){
            if (topicoRepository.findById(datosActualizarRespuesta.topico_id()).isEmpty()){
                throw new ValidacionDeIntegridad("El tópico de la respuesta no fue encontrado. Verifique el id.");
            }
            topico = topicoRepository.findById(datosActualizarRespuesta.topico_id()).get();
        }

        if (datosActualizarRespuesta.usuario_id() != null){
            if (usuarioRepository.findById(datosActualizarRespuesta.usuario_id()).isEmpty()){
                throw new ValidacionDeIntegridad("El usuario de la respuesta no fue encontrado. Verifique el id.");
            }
            usuario = usuarioRepository.findById(datosActualizarRespuesta.usuario_id()).get();
        }

        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarDatos(datosActualizarRespuesta, topico, usuario);

        DatosRetornoRespuesta datosRetornoRespuesta = new DatosRetornoRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getFecha_creacion(), new DatosListadoTopico(respuesta.getTopico()), respuesta.getAutor().getNombre(), respuesta.getSolucion());

        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(url).body(datosRetornoRespuesta);
    }

    public ResponseEntity<Page> listarRespuestas(Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.listarRespuestas(paginacion)
                .map(DatosListadoRespuesta::new));
    }


    public ResponseEntity eliminarRespuesta(Long id) {

        if (respuestaRepository.findById(id).isEmpty()){
            throw new ValidacionDeIntegridad("La respuesta no fue encontrada. Verifique el id.");
        }

        respuestaRepository.borrarRespuesta(id);

        return ResponseEntity.noContent().build();
    }




}
