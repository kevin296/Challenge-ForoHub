package domain.curso;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "curso")
@Entity(name="Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
