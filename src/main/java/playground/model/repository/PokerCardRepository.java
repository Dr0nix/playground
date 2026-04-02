package playground.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playground.model.entity.plain.PokerCard;

import java.util.List;

@Repository
public interface PokerCardRepository extends JpaRepository<PokerCard, Long> {
    List<PokerCard> findByRankNot(int rank);
}
