package playground.apps.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import playground.model.dto.JavaMethodQuizDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/quiz/java-method")
public class JavaMethodQuizCtl {
    private final JavaMethodQuizSvc svc;

    @GetMapping()
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
            @RequestParam(name = "difficulty", required = false, defaultValue = "ALL") String difficulty,
            @RequestParam(name = "roundCount", required = false, defaultValue = "0") String roundCount
    ) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("difficulty", difficulty);
        paramMap.put("roundCount", Integer.parseInt(roundCount));

        List<JavaMethodQuizDTO> quizList = svc.getConditionalQuizSet(paramMap);

        return new ResponseEntity<>(quizList, HttpStatus.OK);
    }
}
