package uz.shaxzod.ticketapp.exceptions;

public class CustomLockedException extends RuntimeException {
    public CustomLockedException(String message) {
        super(message);
    }
}
