package playground.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playground.model.entity.plain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findNoticeByVisibleIsTrue(Pageable pageable);
}
