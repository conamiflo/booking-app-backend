package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.exceptions;
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {
    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
