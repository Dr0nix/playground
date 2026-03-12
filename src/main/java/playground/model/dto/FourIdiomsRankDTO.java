package playground.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FourIdiomsRankDTO {
    private Long userId;
    private String userNickname;
    private Long maxScore;
    private LocalDate created_at;
}
