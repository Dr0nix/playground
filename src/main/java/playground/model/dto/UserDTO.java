package playground.model.dto;

import lombok.Builder;
import lombok.Data;
import playground.model.entity.plain.User;

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

    public UserDTO() {
    }

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .userNm(user.getUserNm())
                .userEmail(user.getUserEmail())
                .userPw(user.getUserPw())
                .useYn(user.getUseYn())
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .modifiedBy(user.getModifiedBy())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

    public static User toUser(UserDTO userDTO) {
        return User.builder()
                .userNm(userDTO.getUserNm())
                .userEmail(userDTO.getUserEmail())
                .userPw(userDTO.getUserPw())
                .useYn(userDTO.getUseYn())
                .createdBy(userDTO.getCreatedBy())
                .createdAt(userDTO.getCreatedAt())
                .modifiedBy(userDTO.getModifiedBy())
                .modifiedAt(userDTO.getModifiedAt())
                .build();
    }
}
