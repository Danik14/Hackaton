package diploma.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma.project.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);

    public boolean existsByUserName(String username);
}
