package devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
