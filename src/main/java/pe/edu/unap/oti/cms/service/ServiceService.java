package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unap.oti.cms.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceService {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    public List<pe.edu.unap.oti.cms.model.Service> findAll() {
        return serviceRepository.findAll();
    }
    
    public Optional<pe.edu.unap.oti.cms.model.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }
    
    public List<pe.edu.unap.oti.cms.model.Service> findActiveServices() {
        return serviceRepository.findByActiveTrueOrderByPriorityAsc();
    }
    
    public List<pe.edu.unap.oti.cms.model.Service> findServicesByTargetAudience(String targetAudience) {
        return serviceRepository.findByTargetAudienceAndActiveTrue(targetAudience);
    }
    
    public List<pe.edu.unap.oti.cms.model.Service> findServicesByCategory(String category) {
        return serviceRepository.findByCategoryAndActiveTrue(category);
    }
    
    public List<pe.edu.unap.oti.cms.model.Service> searchByTitle(String title) {
        return serviceRepository.findByTitleContainingIgnoreCaseAndActiveTrue(title);
    }
    
    public pe.edu.unap.oti.cms.model.Service save(pe.edu.unap.oti.cms.model.Service service) {
        // Set default priority if not provided
        if (service.getPriority() == null) {
            service.setPriority(1);
        }
        
        return serviceRepository.save(service);
    }
    
    public pe.edu.unap.oti.cms.model.Service update(Long id, pe.edu.unap.oti.cms.model.Service serviceData) {
        pe.edu.unap.oti.cms.model.Service existingService = serviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        
        // Update basic fields
        existingService.setTitle(serviceData.getTitle());
        existingService.setDescription(serviceData.getDescription());
        existingService.setFullDescription(serviceData.getFullDescription());
        existingService.setCategory(serviceData.getCategory());
        existingService.setTargetAudience(serviceData.getTargetAudience());
        existingService.setIcon(serviceData.getIcon());
        existingService.setFeatures(serviceData.getFeatures());
        existingService.setActive(serviceData.getActive());
        existingService.setPriority(serviceData.getPriority());
        existingService.setSortOrder(serviceData.getSortOrder());
        existingService.setContactInfo(serviceData.getContactInfo());
        existingService.setRequirements(serviceData.getRequirements());
        
        return serviceRepository.save(existingService);
    }
    
    public void delete(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        serviceRepository.deleteById(id);
    }
    
    public long getActiveServicesCount() {
        return serviceRepository.countByActiveTrue();
    }
    
    public long getTotalServicesCount() {
        return serviceRepository.count();
    }
    
    public void incrementViews(Long id) {
        Optional<pe.edu.unap.oti.cms.model.Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            pe.edu.unap.oti.cms.model.Service service = serviceOptional.get();
            service.setViews(service.getViews() + 1);
            serviceRepository.save(service);
        }
    }
}