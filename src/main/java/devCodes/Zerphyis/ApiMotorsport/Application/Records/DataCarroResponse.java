package devCodes.Zerphyis.ApiMotorsport.Application.Records;

import java.math.BigDecimal;

public record DataCarroResponse(String nome,
                                String modelo,
                                String descricao,
                                BigDecimal preco,
                                String imagemUrl,
                                Integer ordem) {
}
