package playground.apps.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.model.dto.FourIdiomsQuizDTO;
import playground.model.dto.FourIdiomsRankDTO;
import playground.model.mapper.FourIdiomsQuizMapp;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FourIdiomsQuizSvc {
    private final FourIdiomsQuizMapp mapp;

    public FourIdiomsQuizDTO getSingleQuiz(Long quizNo) {
        return mapp.getQuizByNo(quizNo);
    }

    public FourIdiomsQuizDTO getRandomSingleQuiz() {
        Integer count = mapp.getQuizCount();

        Long quizNo = (long) (Math.random() * count) + 1;

        return getSingleQuiz(quizNo);
    }

    public List<FourIdiomsQuizDTO> getQuizList() {
        return mapp.getQuizList();
    }

    List<FourIdiomsQuizDTO> getConditionalQuizSet(int roundCount) {
        List<FourIdiomsQuizDTO> quizList = mapp.getConditionalQuizSet(roundCount);

        return quizList;
    }

    List<FourIdiomsRankDTO> getTopThreeUser(Map<String, Object> paramMap) {
        return mapp.getTopThreeUser(paramMap);
    }
}
