package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unap.oti.cms.model.Staff;
import pe.edu.unap.oti.cms.repository.StaffRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffService {
    
    @Autowired
    private StaffRepository staffRepository;
    
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }
    
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }
    
    public List<Staff> findActiveStaff() {
        return staffRepository.findByActiveTrueOrderByPriorityAsc();
    }
    
    public List<Staff> findStaffByDepartment(String department) {
        return staffRepository.findByDepartmentAndActiveTrueOrderByPriorityAsc(department);
    }
    
    public List<Staff> searchByName(String name) {
        return staffRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }
    
    public List<Staff> searchByPosition(String position) {
        return staffRepository.findByPositionContainingIgnoreCaseAndActiveTrue(position);
    }
    
    public Staff findByEmail(String email) {
        return staffRepository.findByEmailAndActiveTrue(email);
    }
    
    public Staff save(Staff staff) {
        // Set default priority if not provided
        if (staff.getPriority() == null) {
            staff.setPriority(1);
        }
        
        // Validate email uniqueness
        Staff existingStaff = staffRepository.findByEmailAndActiveTrue(staff.getEmail());
        if (existingStaff != null && !existingStaff.getId().equals(staff.getId())) {
            throw new RuntimeException("Ya existe un miembro del personal con este email: " + staff.getEmail());
        }
        
        return staffRepository.save(staff);
    }
    
    public Staff update(Long id, Staff staffData) {
        Staff existingStaff = staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Miembro del personal no encontrado con ID: " + id));
        
        // Validate email uniqueness
        Staff staffWithSameEmail = staffRepository.findByEmailAndActiveTrue(staffData.getEmail());
        if (staffWithSameEmail != null && !staffWithSameEmail.getId().equals(id)) {
            throw new RuntimeException("Ya existe un miembro del personal con este email: " + staffData.getEmail());
        }
        
        // Update basic fields
        existingStaff.setName(staffData.getName());
        existingStaff.setPosition(staffData.getPosition());
        existingStaff.setDepartment(staffData.getDepartment());
        existingStaff.setEmail(staffData.getEmail());
        existingStaff.setPhone(staffData.getPhone());
        existingStaff.setOffice(staffData.getOffice());
        existingStaff.setBio(staffData.getBio());
        existingStaff.setImageUrl(staffData.getImageUrl());
        existingStaff.setSpecialties(staffData.getSpecialties());
        existingStaff.setDegree(staffData.getDegree());
        existingStaff.setExperience(staffData.getExperience());
        existingStaff.setResponsibilities(staffData.getResponsibilities());
        existingStaff.setActive(staffData.getActive());
        existingStaff.setPriority(staffData.getPriority());
        existingStaff.setHireDate(staffData.getHireDate());
        
        return staffRepository.save(existingStaff);
    }
    
    public void delete(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new RuntimeException("Miembro del personal no encontrado con ID: " + id);
        }
        staffRepository.deleteById(id);
    }
    
    public long getTotalStaffCount() {
        return staffRepository.count();
    }
    
    public long getActiveStaffCount() {
        return staffRepository.findByActiveTrueOrderByPriorityAsc().size();
    }
}