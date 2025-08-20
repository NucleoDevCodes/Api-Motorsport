package devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataEspecificacaoTecnicaRequest(@NotNull Long carroId, @NotBlank String titulo, @NotBlank String valor) {
}
