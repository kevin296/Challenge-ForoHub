package domain.topico;

import org.springframework.http.ResponseEntity;

public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorDeTopicos> validadores;

    public ResponseEntity<DatosRespuestaTopico> registrar(DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){

        if (cursoRepository.findByNombreContainsIgnoreCase(datosRegistroTopico.nombreCurso()).isEmpty()){
            throw new ValidacionDeIntegridad("El curso no fue encontrado");
        }

        if (usuarioRepository.findById(datosRegistroTopico.usuario_id()).isEmpty()){
            throw new ValidacionDeIntegridad("El usuario no fue encontrado");
        }

        validadores.forEach(v -> v.validar(datosRegistroTopico));

        var topico = new Topico(datosRegistroTopico);
        topico.setCurso(cursoRepository.findByNombreContainsIgnoreCase(datosRegistroTopico.nombreCurso()).get());
        topico.setAutor(usuarioRepository.findById(datosRegistroTopico.usuario_id()).get());
        Topico topicoRet = topicoRepository.save(topico);

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topicoRet.getId(), topicoRet.getTitulo(),
                topicoRet.getMensaje(), topicoRet.getFecha_creacion().toString(), topicoRet.getEstado().toString(),
                topicoRet.getCurso().getId(), topicoRet.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRet.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    public ResponseEntity<Page> listarTopicos(Pageable paginacion){

        return ResponseEntity.ok(topicoRepository.listarTopicos(paginacion)
                .map(DatosListadoTopicoConRespuestas::new));
    }

    public ResponseEntity<DatosListadoTopicoConRespuestas> listarDetalleTopicos(Long id){

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosListadoTopicoConRespuestas(topico);

        return ResponseEntity.ok(datosTopico);
    }


    public ResponseEntity<RespuestaTopico> actualizarTopico(ActualizarTopico datosActualizarTopico,
                                                            Long id, UriComponentsBuilder uriComponentsBuilder) {

        Curso curso = null;
        Usuario usuario = null;

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        if (ActualizarTopico.nombreCurso() != null) {
            if (cursoRepository.findByNombreContainsIgnoreCase(ActualizarTopico.nombreCurso()).isEmpty()) {
                throw new ValidacionDeIntegridad("El curso no fue encontrado");
            }
            curso = cursoRepository.findByNombreContainsIgnoreCase(ActualizarTopico.nombreCurso()).get();
        }

        if (ActualizarTopico.usuario_id() != null) {
            if (usuarioRepository.findById(ActualizarTopico.usuario_id()).isEmpty()) {
                throw new ValidacionDeIntegridad("El usuario no fue encontrado");
            }
            usuario = usuarioRepository.findById(ActualizarTopico.usuario_id()).get();
        }

        Topico topico = topicoRepository.getReferenceById(id);

        RegistroTopico RegistroTopico = new RegistroTopico(ActualizarTopico.titulo(),
                ActualizarTopico.mensaje(), ActualizarTopico.nombre_Curso(),
                ActualizarTopico.usuario_id());

        validadores.forEach(v -> v.validar(RegistroTopico));

        topico.actualizarDatos(ActualizarTopico, curso, usuario);

        RespuestaTopico RespuestaTopico = new RespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFecha_creacion().toString(), topico.getEstado().toString(),
                topico.getCurso().getId(), topico.getAutor().getId());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(RespuestaTopico);

    }


    public ResponseEntity eliminarTopico(Long id) {

        if (topicoRepository.findById(id).isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no fue encontrado. Verifique el id.");
        }

        topicoRepository.borrarTopico(id);

        return ResponseEntity.noContent().build();
    }

}
