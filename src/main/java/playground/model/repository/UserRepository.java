package playground.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playground.model.entity.plain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNm(String userNm);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserId(Long userId);
}
