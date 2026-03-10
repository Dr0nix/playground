package playground.model.dto;

import java.time.LocalDateTime;

public class QuizDTO {
    private Long quizNo;
    private String questionText;
    private String answerText;
    private Boolean useYn;
    private String createdBy;
    private LocalDateTime createdAt;
}
