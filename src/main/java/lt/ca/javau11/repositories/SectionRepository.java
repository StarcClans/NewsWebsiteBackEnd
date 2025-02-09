package lt.ca.javau11.repositories;

import lt.ca.javau11.entities.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<SectionEntity,String> {
}