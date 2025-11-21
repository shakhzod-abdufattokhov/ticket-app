package uz.shaxzod.ticketapp.models.enums;

import uz.shaxzod.ticketapp.exceptions.CustomIllegalArgumentException;

import java.util.Arrays;
import java.util.Optional;

public enum EventStatus {
    SCHEDULED,
    CANCELED,
    POSTPONED;

    public static boolean contains(String status){
        if(status == null) return false;
        return Arrays.stream(values())
                .anyMatch(s -> s.name().equalsIgnoreCase(status));
    }


//    public static Optional<EventStatus> from(String status){
//        if(status == null) return Optional.empty();
//        try{
//            return Optional.of(EventStatus.valueOf(status.toUpperCase()));
//        }catch (CustomIllegalArgumentException e){
//            return Optional.empty();
//        }
//
//    }
}
