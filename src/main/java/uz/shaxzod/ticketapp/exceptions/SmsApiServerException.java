package uz.shaxzod.ticketapp.exceptions;

public class SmsApiServerException extends RuntimeException {
    public SmsApiServerException(String message) {
        super(message);
    }
}