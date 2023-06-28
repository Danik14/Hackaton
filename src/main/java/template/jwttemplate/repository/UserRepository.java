package template.jwttemplate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import template.jwttemplate.data.User;
import template.jwttemplate.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role = :verifiedRole WHERE u.email = :email")
    int verifyEmail(String email, UserRole verifiedRole);
}
