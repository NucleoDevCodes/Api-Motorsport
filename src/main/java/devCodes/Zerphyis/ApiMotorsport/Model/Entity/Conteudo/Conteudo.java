package devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Conteudo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conteudo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String conteudo;

    @NotBlank
    private String imagemUrl;


}
