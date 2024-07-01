package domain.curso;

public enum Categoria {
    PROGRAMACION("Programacion"),
    DESARROLLO_PERSONAL("Desarrollo_personal"),
    FRONT_END("Front_end"),
    BACK_END("Back_end"),
    LENGUAJES_DE_PROGRAMACION("Lenguajes de Programacion"),
    FRAMEWORKS_Y_BIBLIOTECAS("Frameworks y Bibliotecas"),
    BASES_DE_DATOS("Base de Datos");

    private String categoria;


    Categoria(String categoria){
        this.categoria = categoria;
    }

    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoria.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada " + text);
    }
}
