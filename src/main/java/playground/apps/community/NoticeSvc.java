package playground.apps.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.model.dto.NoticeResDto;
import playground.model.entity.plain.Notice;
import playground.model.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeSvc {

    private final NoticeRepository ntcRepo;

    public Page<NoticeResDto> getNoticePage(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "ntcId")
        );

        Page<Notice> noticeList = ntcRepo.findNoticeByVisibleIsTrue(pageable);

        Page<NoticeResDto> result = noticeList.map(ntc -> new NoticeResDto(
                ntc.getNtcId(),
                ntc.getTitle(),
                ntc.isPinned(),
                ntc.getViewCount(),
                ntc.getRegUserNm(),
                ntc.getRegDttm()
        ));

        return result;
    }

    @Transactional
    public NoticeResDto getSingleNotice(Long ntcId) {
        Notice ntc = ntcRepo.findById(ntcId)
                .orElseThrow(() -> new RuntimeException("ntc not found"));

        ntc.incrementViewCount();

        return new NoticeResDto(
                ntc.getNtcId(),
                ntc.getTitle(),
                ntc.getContent(),
                ntc.isPinned(),
                ntc.getViewCount(),
                ntc.getRegUserNm(),
                ntc.getRegDttm()
        );
    }
}
