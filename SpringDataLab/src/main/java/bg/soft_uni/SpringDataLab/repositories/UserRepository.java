package bg.soft_uni.SpringDataLab.repositories;

import bg.soft_uni.SpringDataLab.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersById(Long id);
    Optional<User> getUserByUsername(String username);
}
