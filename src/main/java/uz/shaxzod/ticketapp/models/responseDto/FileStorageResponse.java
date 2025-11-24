package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileStorageResponse {
    private String id;
    private String url;
    private String compressedUrl;

}
