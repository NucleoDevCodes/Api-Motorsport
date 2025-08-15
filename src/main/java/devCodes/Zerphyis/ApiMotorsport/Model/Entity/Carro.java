package devCodes.Zerphyis.ApiMotorsport.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Carro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String modelo;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @NotBlank
    private String imagemUrl;

    @NotNull
    private Integer ordem;

}
