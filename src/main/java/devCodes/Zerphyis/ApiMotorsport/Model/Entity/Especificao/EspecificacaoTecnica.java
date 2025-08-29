package devCodes.Zerphyis.ApiMotorsport.Model.Entity.Especificao;

import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Especificacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EspecificacaoTecnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @NotBlank
    private String titulo;

    @NotBlank
    private  String valor;

}
