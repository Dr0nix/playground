package playground.apps.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.model.dto.JavaMethodQuizDTO;
import playground.model.mapper.JavaMethodQuizMapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class JavaMethodQuizSvc {

    private final JavaMethodQuizMapp mapp;

    public JavaMethodQuizDTO getSingleQuiz(Long quizNo) {
        return mapp.getQuizByNo(quizNo);
    }

    public JavaMethodQuizDTO getRandomSingleQuiz() {
        Integer count = mapp.getQuizCount();

        Long quizNo = (long) (Math.random() * count) + 1;

        return getSingleQuiz(quizNo);
    }

    public List<JavaMethodQuizDTO> getConditionalQuizSet(Map<String, Object> paramMap) {
        List<JavaMethodQuizDTO> guizList = mapp.getConditionalQuizSet(paramMap);

        return guizList;
    }

    public List<JavaMethodQuizDTO> getQuizList() {
        return mapp.getQuizList();
    }
}
