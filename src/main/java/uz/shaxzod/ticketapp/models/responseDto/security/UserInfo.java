package uz.shaxzod.ticketapp.models.responseDto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.Role;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInfo {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Role role;
}