package controller;


import domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaTopico> registrarTopico(@RequestBody @Valid RegistroTopico registroTopico, UriComponentsBuilder uriComponentsBuilder){

        return  service.registrar(registroTopico, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion){

        return service.listarTopicos(paginacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListadoRespuestaConTopico> listarDetalleTopico(@PathVariable Long id){

        return service.listarDetalleTopicos(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaTopico> actualizaTopico(@RequestBody @Valid ActualizarTopico actualizarTopico, @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){

        return service.actualizarTopico(actualizarTopico, id, uriComponentsBuilder);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){

        return service.eliminarTopico(id);
    }

}
