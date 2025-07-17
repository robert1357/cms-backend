package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.unap.oti.cms.model.Multimedia;

import java.util.List;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
    
    // Find active multimedia ordered by creation date
    List<Multimedia> findByActiveTrueOrderByCreatedAtDesc();
    
    // Find public multimedia ordered by creation date
    List<Multimedia> findByIsPublicTrueOrderByCreatedAtDesc();
    
    // Find multimedia by file type
    List<Multimedia> findByFileTypeAndActiveTrueOrderByCreatedAtDesc(String fileType);
    
    // Find public multimedia by file type
    List<Multimedia> findByFileTypeAndIsPublicTrueOrderByCreatedAtDesc(String fileType);
    
    // Find multimedia by category
    List<Multimedia> findByCategoryAndActiveTrueOrderByCreatedAtDesc(String category);
    
    // Find public multimedia by category
    List<Multimedia> findByCategoryAndIsPublicTrueOrderByCreatedAtDesc(String category);
    
    // Find multimedia by title (case insensitive)
    List<Multimedia> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
    
    // Find multimedia by uploaded by
    List<Multimedia> findByUploadedByOrderByCreatedAtDesc(String uploadedBy);
    
    // Find featured multimedia
    List<Multimedia> findByActiveTrueAndFeaturedTrueOrderByCreatedAtDesc();
    
    // Increment download count
    @Modifying
    @Query("UPDATE Multimedia m SET m.downloadCount = m.downloadCount + 1 WHERE m.id = :id")
    void incrementDownloadCount(Long id);
    
    // Find gallery items (images and videos)
    @Query("SELECT m FROM Multimedia m WHERE m.active = true AND m.fileType IN ('image', 'video') ORDER BY m.createdAt DESC")
    List<Multimedia> findGalleryItems();
    
    // Find documents
    @Query("SELECT m FROM Multimedia m WHERE m.active = true AND m.fileType = 'document' ORDER BY m.createdAt DESC")
    List<Multimedia> findDocuments();
}