/*
package uz.shaxzod.ticketapp.models.enums;

import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;

public enum SeatType{
    REGULAR,
    VIP;

    public static SeatType of(String type) {
        if(type == null){
            return SeatType.REGULAR;
        }
        try{
            return SeatType.valueOf(type.toUpperCase());
        }catch (RuntimeException e){
            throw new CustomBadRequestException("Seat type is not valid: "+ type);
        }
    }
}
*/
