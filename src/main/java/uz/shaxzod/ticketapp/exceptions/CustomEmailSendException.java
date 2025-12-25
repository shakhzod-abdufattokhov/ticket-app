package uz.shaxzod.ticketapp.exceptions;

public class CustomEmailSendException extends RuntimeException{
    public CustomEmailSendException(String message) {
        super(message);
    }
}
