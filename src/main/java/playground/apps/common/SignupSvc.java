package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import playground.model.dto.UserDTO;
import playground.model.entity.plain.User;
import playground.model.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignupSvc {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Transactional
    public boolean createUser(UserDTO userDTO) {
        boolean result = false;

        String hashedPw = encoder.encode(userDTO.getUserPw());

        User user = User.builder()
                .userNm(userDTO.getUserNm())
                .userEmail(userDTO.getUserEmail())
                .userPw(hashedPw)
                .useYn(true)
                .createdBy(userDTO.getUserNm())
                .createdAt(LocalDateTime.now())
                .modifiedBy(userDTO.getUserNm())
                .modifiedAt(LocalDateTime.now())
                .build();

        try {
            repo.save(user);
            result = true;
        } catch (TransactionSystemException e) {
            log.info("{}", e.getMessage());
        }

        return result;
    }
}
