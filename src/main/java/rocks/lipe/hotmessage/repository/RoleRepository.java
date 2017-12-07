package rocks.lipe.hotmessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rocks.lipe.hotmessage.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByDescription(String name);

}
