package devCodes.Zerphyis.ApiMotorsport.Application.Records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataCarroRequest(@NotBlank String nome,
                               @NotBlank String modelo,
                               @NotBlank String descricao,
                               @NotNull BigDecimal preco,
                               @NotBlank String imagemUrl,
                               @NotNull Integer ordem) {
}
