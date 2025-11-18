package uz.shaxzod.ticketapp.service;

import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.shaxzod.ticketapp.models.entity.FileStorage;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;


import java.util.List;

public interface FileStorageService {
    ResponseEntity<FileUrlResource> uploadFile(Long id);
    ResponseEntity<FileUrlResource> uploadCompressedFile(Long id);
    FileStorage getFile(Long id);
    List<Long> saveFiles(List<MultipartFile> files);
//    void updateItemImages(List<Long> fileStorageIds, Long itemId);
//    void createItemImageList(Long itemId, List<Long> fileStorageIds);
//    FileStorage getItemMainImage(Long itemId);
//    ApiResponse<Void> changeMainItemImage(Long fileStorageId);
//    List<FileStorage> getItemImages(Long itemId);
    Long saveFile(MultipartFile file);
    ApiResponse<Void> deleteFile(Long id);
}
