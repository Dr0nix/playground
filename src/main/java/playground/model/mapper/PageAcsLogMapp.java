package playground.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import playground.model.dto.PageAcsLogRequestDTO;
import playground.model.dto.PageAcsLogResponseDTO;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PageAcsLogMapp {
    List<PageAcsLogResponseDTO> getAllPageAcsLog();

    List<PageAcsLogResponseDTO> searchPageAcsLog(Map<String, Object> params);

    void insertPageAcsLog(PageAcsLogRequestDTO dto);
}
