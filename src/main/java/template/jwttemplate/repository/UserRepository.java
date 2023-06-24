package template.jwttemplate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import template.jwttemplate.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public Optional<User> findByEmail(String email);
}
