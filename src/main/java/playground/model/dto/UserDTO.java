package playground.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String userNm;
    private String userEmail;
    private String userPw;
    private Boolean useYn;
    private String createdBy;
    private LocalDateTime createdAt;
    private String modifiedBy;
    private LocalDateTime modifiedAt;


    @Builder
    public UserDTO(String userNm, String userEmail, String userPw, Boolean useYn, String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.useYn = useYn;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }
}
