package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import playground.model.entity.plain.User;
import playground.model.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginSvc implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return userRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));
    }
}
