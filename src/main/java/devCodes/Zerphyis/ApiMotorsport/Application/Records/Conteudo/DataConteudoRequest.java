package devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo;

import jakarta.validation.constraints.NotBlank;

public record DataConteudoRequest(@NotBlank String titulo,
                                  @NotBlank String conteudo,
                                  @NotBlank String imagemUrl) {
}
