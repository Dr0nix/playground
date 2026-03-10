package playground.model.dto;

import lombok.Data;

@Data
public class JavaMethodQuizDTO extends QuizDTO{
    private String explanation;
    private String difficultyCd;
}
