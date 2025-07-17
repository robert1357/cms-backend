package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUploadService {
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );
    
    private static final List<String> ALLOWED_DOCUMENT_TYPES = Arrays.asList(
        "application/pdf", "application/msword", 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );
    
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    
    public String uploadFile(MultipartFile file) throws IOException {
        // Validate file
        validateFile(file);
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + sanitizeFilename(originalFilename);
        
        // Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Return URL path
        return "/uploads/" + fileName;
    }
    
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("El archivo es demasiado grande. Máximo permitido: 10MB");
        }
        
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new RuntimeException("Tipo de archivo no válido");
        }
        
        if (!ALLOWED_IMAGE_TYPES.contains(contentType) && !ALLOWED_DOCUMENT_TYPES.contains(contentType)) {
            throw new RuntimeException("Tipo de archivo no permitido: " + contentType);
        }
    }
    
    private String sanitizeFilename(String filename) {
        // Remove spaces and special characters
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
    
    public String getFileType(String contentType) {
        if (ALLOWED_IMAGE_TYPES.contains(contentType)) {
            return "image";
        } else if (ALLOWED_DOCUMENT_TYPES.contains(contentType)) {
            return "document";
        } else {
            return "other";
        }
    }
}