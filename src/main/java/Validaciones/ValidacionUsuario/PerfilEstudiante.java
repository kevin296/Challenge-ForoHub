package Validaciones.ValidacionUsuario;

import domain.user.Perfiles;
import domain.user.RegistroUsuario;
import jakarta.validation.ValidationException;

public class PerfilEstudiante implements ValidacionUsuario {

    
    @Override
    public void validar(RegistroUsuario registroUsuario) {

        if (!registroUsuario.perfil().equalsIgnoreCase(String.valueOf(Perfiles.ESTUDIANTE))){
            throw new ValidationException("Sólo se puede registrar como Estudiante");
        }
    }
}
