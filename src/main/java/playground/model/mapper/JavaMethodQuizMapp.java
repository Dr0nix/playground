package playground.model.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import playground.model.dto.JavaMethodQuizDTO;

import java.util.List;

@Mapper
@Repository
public interface JavaMethodQuizMapp {
    Integer getQuizCount();

    JavaMethodQuizDTO getQuizByNo(Long quizNo);

    List<JavaMethodQuizDTO> getQuizList();
}
