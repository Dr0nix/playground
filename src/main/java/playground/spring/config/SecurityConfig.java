package playground.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**","/js/**","/img/**","/favicon.ico", "/webjars/**").permitAll()
                        .requestMatchers("/login-page", "/login","/signup-page").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-page")              // ← 내 페이지
                        .loginProcessingUrl("/login")     // ← POST 처리 URL (폼 action과 동일)
                        .defaultSuccessUrl("/gotopage/6", true) // 로그인 성공 후 이동
                        .failureUrl("/login?error")       // 실패 시
                        .permitAll()
                )
                .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout"))
        // 필요 시 CSRF 조정
        //.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
