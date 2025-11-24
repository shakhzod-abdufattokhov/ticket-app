package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.FileStorage;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, String> {
//    @Query("""
//        select f from FileStorage f
//        join ItemImage i on f.id = i.image.id
//        where i.itemId = :itemId
//        and i.isMain = true
//        order by i.id
//        limit 1
//""")
//    Optional<FileStorage> getMainFileStorage(Long itemId);

//    @Query("""
//        select f from FileStorage f
//        join f.itemImage i
//        where i.itemId = :itemId
//        order by i.id
//""")
//    List<FileStorage> getItemImages(Long itemId);
}
