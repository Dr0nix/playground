package playground.apps.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.model.dto.FourIdiomsQuizDTO;
import playground.model.dto.FourIdiomsRankDTO;
import playground.model.mapper.FourIdiomsQuizMapp;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static playground.utils.UsetUtil.getLoginUserId;

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

    List<FourIdiomsRankDTO> getTopThreeUser() {
        Map<String, Object> paramMap = getSeasonPeriod();

        return mapp.getTopThreeUser(paramMap);
    }

    private Map<String, Object> getSeasonPeriod() {
        Map<String, Object> paramMap = new HashMap<>();

        String from = "2026-01-01";
        String to = "2026-06-01";


        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        paramMap.put("from", fromDate);
        paramMap.put("to", toDate);

        return paramMap;
    }

    public void updateUserMaxScore(Long score) {
        Long userId = getLoginUserId();

        if(userId == null || userId == 1L) {
            return;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("score", score);
        paramMap.put("userId", userId);

        mapp.updateUserMaxScore(paramMap);
    }
}
