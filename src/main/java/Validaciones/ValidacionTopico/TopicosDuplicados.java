package Validaciones.ValidacionTopico;

import domain.topico.RegistroTopico;
import domain.topico.Topico;
import domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicosDuplicados implements ValidacionTopico {
    @Autowired
    public TopicoRepository topicoRepository;

    @Override
    public void validar(RegistroTopico registroTopico) {
        List<Topico> topicos = topicoRepository.findByTitulo(registroTopico.titulo());
        topicos.stream()
                .filter(t -> t.getMensaje().equalsIgnoreCase(registroTopico.mensaje()))
                .findFirst()
                .ifPresent(t -> {
                    throw new ValidationException("No se pueden crear tópicos duplicados");
                });
    }
}
