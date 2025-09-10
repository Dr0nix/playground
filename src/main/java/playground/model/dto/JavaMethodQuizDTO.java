package playground.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JavaMethodQuizDTO {
    private Long quizNo;
    private String questionText;
    private String answerText;
    private String explanation;
    private Boolean useYn;
    private String createdBy;
    private LocalDateTime createdAt;
}
