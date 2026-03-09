package playground.model.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import playground.model.dto.JavaMethodQuizDTO;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface JavaMethodQuizMapp {
    Integer getQuizCount();

    JavaMethodQuizDTO getQuizByNo(Long quizNo);

    List<JavaMethodQuizDTO> getQuizList();

    List<JavaMethodQuizDTO> getConditionalQuizSet(Map<String, Object> paramMap);
}
