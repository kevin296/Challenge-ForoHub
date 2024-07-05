package controller;


import domain.respuesta.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<RetornoRespuesta> registrarRespuesta(@RequestBody @Valid RegistroRespuesta registroRespuesta, UriComponentsBuilder uriComponentsBuilder){

        return  service.registroRespuesta(registroRespuesta, uriComponentsBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RetornoRespuesta> actualizaRespuesta(@RequestBody @Valid ActualizarRespuesta actualizarRespuesta,
                                                                    @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){

        return service.actualizarRespuesta(actualizarRespuesta, id, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page> listadoRespuesta(@PageableDefault(size = 10) Pageable paginacion){

        return service.listarRespuestas(paginacion);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){

        return service.eliminarRespuesta(id);
    }



}
