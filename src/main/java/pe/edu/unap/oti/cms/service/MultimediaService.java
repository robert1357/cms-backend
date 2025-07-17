package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unap.oti.cms.model.Multimedia;
import pe.edu.unap.oti.cms.repository.MultimediaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MultimediaService {
    
    @Autowired
    private MultimediaRepository multimediaRepository;
    
    public List<Multimedia> findAll() {
        return multimediaRepository.findAll();
    }
    
    public Optional<Multimedia> findById(Long id) {
        return multimediaRepository.findById(id);
    }
    
    public List<Multimedia> findActiveMultimedia() {
        return multimediaRepository.findByActiveTrueOrderByCreatedAtDesc();
    }
    
    public List<Multimedia> findMultimediaByFileType(String fileType) {
        return multimediaRepository.findByFileTypeAndActiveTrueOrderByCreatedAtDesc(fileType);
    }
    
    public List<Multimedia> findMultimediaByCategory(String category) {
        return multimediaRepository.findByCategoryAndActiveTrueOrderByCreatedAtDesc(category);
    }
    
    public List<Multimedia> searchByTitle(String title) {
        return multimediaRepository.findByTitleContainingIgnoreCaseAndActiveTrue(title);
    }
    
    public List<Multimedia> findMultimediaByUploadedBy(String uploadedBy) {
        return multimediaRepository.findByUploadedByOrderByCreatedAtDesc(uploadedBy);
    }
    
    public List<Multimedia> findFeaturedMultimedia() {
        return multimediaRepository.findByActiveTrueAndFeaturedTrueOrderByCreatedAtDesc();
    }
    
    public List<Multimedia> findGalleryItems() {
        return multimediaRepository.findGalleryItems();
    }
    
    public List<Multimedia> findDocuments() {
        return multimediaRepository.findDocuments();
    }
    
    public Multimedia save(Multimedia multimedia) {
        // Set default values
        if (multimedia.getUploadedBy() == null || multimedia.getUploadedBy().isEmpty()) {
            multimedia.setUploadedBy("Administrador");
        }
        
        if (multimedia.getDownloadCount() == null) {
            multimedia.setDownloadCount(0);
        }
        
        return multimediaRepository.save(multimedia);
    }
    
    public Multimedia update(Long id, Multimedia multimediaData) {
        Multimedia existingMultimedia = multimediaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multimedia no encontrado con ID: " + id));
        
        // Update basic fields
        existingMultimedia.setTitle(multimediaData.getTitle());
        existingMultimedia.setDescription(multimediaData.getDescription());
        existingMultimedia.setFileUrl(multimediaData.getFileUrl());
        existingMultimedia.setThumbnailUrl(multimediaData.getThumbnailUrl());
        existingMultimedia.setFileType(multimediaData.getFileType());
        existingMultimedia.setFileSize(multimediaData.getFileSize());
        existingMultimedia.setCategory(multimediaData.getCategory());
        existingMultimedia.setTags(multimediaData.getTags());
        existingMultimedia.setAlt(multimediaData.getAlt());
        existingMultimedia.setCaption(multimediaData.getCaption());
        existingMultimedia.setActive(multimediaData.getActive());
        existingMultimedia.setFeatured(multimediaData.getFeatured());
        
        return multimediaRepository.save(existingMultimedia);
    }
    
    public void delete(Long id) {
        if (!multimediaRepository.existsById(id)) {
            throw new RuntimeException("Multimedia no encontrado con ID: " + id);
        }
        multimediaRepository.deleteById(id);
    }
    
    @Transactional
    public void incrementDownloadCount(Long id) {
        multimediaRepository.incrementDownloadCount(id);
    }
    
    public long getTotalMultimediaCount() {
        return multimediaRepository.count();
    }
    
    public long getActiveMultimediaCount() {
        return multimediaRepository.findByActiveTrueOrderByCreatedAtDesc().size();
    }
}