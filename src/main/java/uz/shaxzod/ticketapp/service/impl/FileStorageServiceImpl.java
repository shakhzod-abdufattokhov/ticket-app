package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.shaxzod.ticketapp.exceptions.CustomInternalErrorException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.FileStorage;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.repository.FileStorageRepository;
import uz.shaxzod.ticketapp.service.FileStorageService;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Value("${file.storage.path}")
    private String baseUrl;

    @Override
    public ResponseEntity<FileUrlResource> uploadFile(Long id) {
        return getFileUrlResource(id, false);
    }

    @Override
    public ResponseEntity<FileUrlResource> uploadCompressedFile(Long id) {
        return getFileUrlResource(id, true);
    }

/*    @Override
    public List<FileStorage> getItemImages(Long itemId) {
        return fileStorageRepository.getItemImages(itemId);
    }*/

    @Override
    public List<Long> saveFiles(List<MultipartFile> files) {
        if(files == null || files.isEmpty()) {
            return Collections.emptyList();
        }
        // setting image main
        List<Long> ids = new ArrayList<>();
        ids.add(save(files.get(0)));

        for(int i = 1; i < files.size(); i++) {
            ids.add(save(files.get(i)));
        }
        return ids;
    }


//    @Override
//    public void createItemImageList(Long itemId, List<Long> fileStorageIds) {
//        for(int i = 0; i < fileStorageIds.size(); i++) {
//            ItemImage itemImage = new ItemImage();
//            itemImage.setItemId(itemId);
//            itemImage.setImage(getFile(fileStorageIds.get(i)));
//            itemImage.setIsMain(i == 0);
//            itemImageRepository.save(itemImage);
//        }
//    }

//    @Override
//    public FileStorage getItemMainImage(Long itemId) {
//        return fileStorageRepository.getMainFileStorage(itemId).orElseThrow(() -> new CustomNotFoundException("File not found"));
//    }

//    @Override
//    public ApiResponse<Void> changeMainItemImage(Long fileStorageId) {
//
//        FileStorage fileStorage = getFile(fileStorageId);
//        ItemImage itemImage = fileStorage.getItemImage();
//        if(itemImage == null) {
//            throw new CustomInternalErrorException("Item image not found with file storage id: " + fileStorageId);
//        }
//        itemImageRepository.setIsMainFalse(itemImage.getItemId(), itemImage.getImage().getId());
//        return new ApiResponse<Void>().success();
//    }

    @Override
    public Long saveFile(MultipartFile file) {
        return save(file);
    }

    @Override
    public FileStorage getFile(Long id) {
        return fileStorageRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("File not found with this id: " + id));
    }

    @Override
    public ApiResponse<Void> deleteFile(Long id) {

        FileStorage byId = getFile(id);
        try {
            Files.delete(Paths.get(byId.getPath()));
        } catch (IOException e) {
            throw new CustomInternalErrorException("Could not delete file. " + e.getMessage());
        }

        fileStorageRepository.deleteById(id);
        return ApiResponse.success("Deleted successfully");
    }

//    @Override
//    public void updateItemImages(List<Long> fileStorageIds, Long itemId) {
//        List<FileStorage> itemImages = fileStorageRepository.getItemImages(itemId);
//
//        for(FileStorage fs : itemImages) {
//            if (!fileStorageIds.contains(fs.getId())) {
//                deleteFile(fs.getId());
//            }
//        }
//    }

    private Long save(MultipartFile multipartFile) {
        validateFile(multipartFile);

        String fileExtension = getFileExtension(multipartFile.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String uniqueOriginalFileName = LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue()+ "/" + uuid + "."+ fileExtension;
        String uniqueCompressedFileName = LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue()+ "/" + uuid + "_compressed"+ "." + fileExtension;

        Path originalFilePath = Paths.get(baseUrl, uniqueOriginalFileName);
        Path compressedFilePath = Paths.get(baseUrl, uniqueCompressedFileName);

        try {
            Files.createDirectories(originalFilePath.getParent());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, originalFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            if (isCompressible(fileExtension))
                compressAndSaveImage(multipartFile, compressedFilePath, fileExtension);
            else {
                compressedFilePath = originalFilePath;
            }
        } catch (IOException e) {
            throw new CustomInternalErrorException("Could not store file. Please try again.");
        }


        FileStorage originalFileStorage = new FileStorage();
        originalFileStorage.setName(uuid.toString());
        originalFileStorage.setPath(originalFilePath.toString());
        originalFileStorage.setCompressedPath(compressedFilePath.toString());
        originalFileStorage.setType(multipartFile.getContentType());
        originalFileStorage = fileStorageRepository.save(originalFileStorage);

        return originalFileStorage.getId();
    }




    private void compressAndSaveImage(MultipartFile file, Path compressedFilePath, String fileExtension) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExtension.replace(".", ""));
        if (!writers.hasNext()) {
            throw new UnsupportedOperationException("No writer available for format: " + fileExtension);
        }
        ImageWriter writer = writers.next();

        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressedFilePath.toFile());
        writer.setOutput(outputStream);

        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        if (writeParam.canWriteCompressed()) {
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(0.2f); // 20% quality for compression
        }

        writer.write(null, new IIOImage(originalImage, null, null), writeParam);

        outputStream.close();
        writer.dispose();
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.')+1);
        }
        return "";
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomInternalErrorException("Cannot store empty file.");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new CustomInternalErrorException("File size exceeds limit of 5MB.");
        }
    }

    @NotNull
    private ResponseEntity<FileUrlResource> getFileUrlResource(Long id, boolean compressed) {
        FileStorage fileStorage = getFile(id);
        String path = compressed ? fileStorage.getCompressedPath() : fileStorage.getPath();
        if(compressed && path == null) {
            path = fileStorage.getPath();
        }

        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileStorage.getName() + "\"")
                    .contentType(MediaType.parseMediaType(fileStorage.getType()))
                    .body(new FileUrlResource(path));
        } catch (MalformedURLException e) {
            throw new CustomInternalErrorException("Could not load the file. " + e.getMessage());
        }
    }

    private boolean isCompressible(String fileExtension) {
        // List of compressible raster formats
        return List.of("jpg", "jpeg", "png", "bmp", "tiff", "webp", "gif").contains(fileExtension);
    }

}
