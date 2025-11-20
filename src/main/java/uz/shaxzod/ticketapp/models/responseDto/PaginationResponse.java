package uz.shaxzod.ticketapp.models.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse {
    private int element;
    private int page;
    private Object data;

    public PaginationResponse(){
        this.element = 0;
        this.page = 0;
        this.data = Collections.emptyList();
    }
}
