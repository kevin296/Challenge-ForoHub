package domain.topico;

public enum Estado {
    SOLUCIONADO("Solucionado"),
    SIN_SOLUCION("En Espera");

    private final String status;

    Estado(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Estado fromString(String text) {
        for (Estado estado : Estado.values()) {
            if (estado.status.equalsIgnoreCase(text)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Ningún estado encontrado: " + text);
    }
}
