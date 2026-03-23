package playground.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import playground.apps.common.LoginSvc;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    };

    @Bean
    SecurityFilterChain security(HttpSecurity http, LoginSvc loginSvc) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**","/js/**","/img/**","/favicon.ico", "/webjars/**").permitAll()
                        .requestMatchers("/login-page", "/login","/signup-page").permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(f -> f
                        .loginPage("/login-page")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userEmail")   // 예: 이메일을 아이디로
                        .passwordParameter("userPw")
                        .defaultSuccessUrl("/gotopage/6", true)
                        .failureUrl("/login-page?error")
                        .permitAll()
                )
                .userDetailsService(loginSvc)
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
