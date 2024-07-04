package domain.user;

public enum Perfiles {
    ADMINISTRADOR("Administrador"),
    INSTRUCTOR("Instructor"),
    ESTUDIANTE("Estudiante");

    private String perfil;

    Perfiles(String perfiles){
        this.perfiles = perfiles;
    }

    public static Perfiles fromString(String text){
        for(Perfiles perfiles: Perfiles.values()){
            if (perfiles.perfiles.equalsIgnoreCase(text)){
                return perfiles;
            }
        }
        throw new IllegalArgumentException("Ningún perfil fue encontrado: " + text);
    }
}
