package uz.shaxzod.ticketapp.exceptions;

public class CustomInvalidDtoException extends RuntimeException {
    public CustomInvalidDtoException(String message) {
        super(message);
    }
}