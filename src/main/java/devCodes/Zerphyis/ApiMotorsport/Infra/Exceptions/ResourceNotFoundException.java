package devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
