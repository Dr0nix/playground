package playground.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playground.model.entity.plain.Menu;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByMenuNo(Long menuNo);

    List<Menu> findByUseYnTrueOrderByMenuLvAscPrntNoAscSortOrdAsc();
}
