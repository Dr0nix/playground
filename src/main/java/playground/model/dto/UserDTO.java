package playground.model.dto;

import lombok.Builder;
import lombok.Data;
import playground.model.entity.plain.User;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String userNm;
    private String userNickname;
    private String userEmail;
    private String userPw;
    private Boolean useYn;
    private String createdBy;
    private LocalDateTime createdAt;
    private String modifiedBy;
    private LocalDateTime modifiedAt;


    @Builder
    public UserDTO(Long userId, String userNm, String userNickname, String userEmail, String userPw, Boolean useYn, String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userNm = userNm;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.useYn = useYn;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

    public UserDTO() {
    }
}
