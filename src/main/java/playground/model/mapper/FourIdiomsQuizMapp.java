package playground.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import playground.model.dto.FourIdiomsQuizDTO;

import java.util.List;

@Mapper
@Repository
public interface FourIdiomsQuizMapp {
    Integer getQuizCount();

    FourIdiomsQuizDTO getQuizByNo(Long quizNo);

    List<FourIdiomsQuizDTO> getQuizList();

    List<FourIdiomsQuizDTO> getConditionalQuizSet(int roundCount);
}
