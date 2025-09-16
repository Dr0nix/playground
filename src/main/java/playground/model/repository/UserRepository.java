package playground.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playground.model.entity.plain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserNm(String userNm);
}
