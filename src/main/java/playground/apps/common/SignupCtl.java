package playground.apps.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import playground.model.dto.UserDTO;
import playground.model.entity.plain.User;

@Tag(name = "Signup", description = "회원가입 API")
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/signup")
public class SignupCtl {

    private final SignupSvc svc;

    @PostMapping
    public ResponseEntity<?> signupUser(
            @RequestBody UserDTO userDTO) {
        log.info("signupUser: {}", userDTO.getUserNm());
        boolean result = svc.createUser(userDTO);

        if(result) {
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
