package uz.shaxzod.ticketapp.models.enums;

import uz.shaxzod.ticketapp.exceptions.CustomIllegalArgumentException;

import java.util.Arrays;

public enum EventType {
    CONCERT,
    TRIP,
    THEATER;

    public static boolean contains(String type){
        if(type == null) return false;

        return Arrays.stream(values())
                .anyMatch(t -> t.name().equalsIgnoreCase(type));
    }

    public static EventType from(String type) {
        if(type == null) return EventType.CONCERT;
        try{
            return EventType.valueOf(type.toUpperCase());
        }catch (CustomIllegalArgumentException e){
            return EventType.CONCERT;
        }
    }
}
