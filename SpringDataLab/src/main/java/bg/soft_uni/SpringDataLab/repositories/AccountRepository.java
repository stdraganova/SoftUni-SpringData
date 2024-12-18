package bg.soft_uni.SpringDataLab.repositories;

import bg.soft_uni.SpringDataLab.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountById(Long id);
}
