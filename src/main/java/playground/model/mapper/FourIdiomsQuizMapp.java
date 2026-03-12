package playground.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import playground.model.dto.FourIdiomsQuizDTO;
import playground.model.dto.FourIdiomsRankDTO;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface FourIdiomsQuizMapp {
    Integer getQuizCount();

    FourIdiomsQuizDTO getQuizByNo(Long quizNo);

    List<FourIdiomsQuizDTO> getQuizList();

    List<FourIdiomsQuizDTO> getConditionalQuizSet(int roundCount);

    List<FourIdiomsRankDTO> getTopThreeUser(Map<String, Object> paramMap) ;
}
