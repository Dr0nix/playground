package playground.apps.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.model.dto.PageAcsLogRequestDTO;
import playground.model.dto.PageAcsLogResponseDTO;
import playground.model.mapper.PageAcsLogMapp;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PageAcsLogSvc {
    private final PageAcsLogMapp mapp;

    public List<PageAcsLogResponseDTO> getAllPageAcsLog() {
        return mapp.getAllPageAcsLog();
    }

    public List<PageAcsLogResponseDTO> searchPageAcsLog(Map<String, Object> paramMap) {
        return mapp.searchPageAcsLog(paramMap);
    }

    public void insertPageAcsLog(PageAcsLogRequestDTO dto) {
        mapp.insertPageAcsLog(dto);
    }
}
