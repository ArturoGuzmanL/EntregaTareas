import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Alumno implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String telefono;
    private String email;
    private Double ad;
    private Double di;
}
