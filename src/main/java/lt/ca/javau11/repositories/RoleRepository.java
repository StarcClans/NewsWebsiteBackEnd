package lt.ca.javau11.repositories;

import lt.ca.javau11.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name); // Add this
}