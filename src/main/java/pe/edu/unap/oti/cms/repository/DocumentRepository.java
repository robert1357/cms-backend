package pe.edu.unap.oti.cms.repository;

import pe.edu.unap.oti.cms.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}