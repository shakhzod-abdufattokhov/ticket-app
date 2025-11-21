package uz.shaxzod.ticketapp.models.enums;

public enum Language {
    UZ,
    RU,
    EN;

    public static Language from(String language) {
        if(language == null) return Language.UZ;
        try{
            return Language.valueOf(language.toUpperCase());
        }catch (IllegalArgumentException e){
            return Language.UZ;
        }
    }
}
