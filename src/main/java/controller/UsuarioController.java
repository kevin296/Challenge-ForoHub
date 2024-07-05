package controller;


import domain.user.ActualizarUsuario;
import domain.user.RegistroUsuario;
import domain.user.RespuestaUsuario;
import domain.user.UsuarioService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaUsuario> registrarUsuario(@RequestBody @Valid RegistroUsuario registroUsuario, UriComponentsBuilder uriComponentsBuilder){
        return service.registrarUsuario(registroUsuario, uriComponentsBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaUsuario> actualizarUsuario(@RequestBody ActualizarUsuario actualizarUsuario, @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){
        return service.actualizarUsuario(actualizarUsuario, id, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion){

        return service.listarUsuarios(paginacion);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){

        return service.eliminarUsuario(id);
    }



}
