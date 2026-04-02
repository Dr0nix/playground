package playground.apps.quiz;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import playground.model.dto.FourIdiomsQuizDTO;

import java.util.List;

@Tag(name = "FourIdiomsQuiz", description = "사자성어 퀴즈 및 랭킹 관련 API")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/quiz/four-idioms")
public class FourIdiomsQuizCtl {
    private final FourIdiomsQuizSvc svc;

    @GetMapping
    public ResponseEntity<?> getRandomQuiz() {
        return new ResponseEntity<>(svc.getRandomSingleQuiz(), HttpStatus.OK);
    }

    @GetMapping("/{quizNo}")
    public ResponseEntity<?> getSingleQuiz(@PathVariable Long quizNo) {
        return new ResponseEntity<>(svc.getSingleQuiz(quizNo), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuiz() {
        return new ResponseEntity<>(svc.getQuizList(), HttpStatus.OK);
    }

    @GetMapping("/start")
    public ResponseEntity<?> getStartQuiz(
            @RequestParam(name = "roundCount", required = false, defaultValue = "0") int roundCount
    ) {

        List<FourIdiomsQuizDTO> quizList = svc.getConditionalQuizSet(roundCount);

        return new ResponseEntity<>(quizList, HttpStatus.OK);
    }

    @GetMapping("/rank")
    public ResponseEntity<?> getTopThreeUser() {
        return new ResponseEntity<>(svc.getTopThreeUser(), HttpStatus.OK);
    }

    @PostMapping("/rank")
    public ResponseEntity<?> updateUserMaxScore(
            @RequestBody Long score
    ) {
        svc.updateUserMaxScore(score);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
