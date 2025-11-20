package uz.shaxzod.ticketapp.models.enums;

import uz.shaxzod.ticketapp.exceptions.CustomIllegalArgumentException;

import java.util.Optional;

public enum EventStatus {
    SCHEDULED,
    CANCELED,
    POSTPONED;

    public static Optional<EventStatus> from(String status){
        if(status == null) return Optional.empty();
        try{
            return Optional.of(EventStatus.valueOf(status.toUpperCase()));
        }catch (CustomIllegalArgumentException e){
            return Optional.empty();
        }

    }
}
