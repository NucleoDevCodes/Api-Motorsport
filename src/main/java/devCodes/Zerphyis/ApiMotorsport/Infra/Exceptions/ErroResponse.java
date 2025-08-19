package devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions;

import java.time.LocalDateTime;

public record ErroResponse(String message,
                           int status,
                           LocalDateTime timestamp) {
}
