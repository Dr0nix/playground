package playground.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeReqDto {
    private String title;
    private String content;
    private boolean pinYn;
}
