package project.wideWebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // SecurityFilterChain Bean 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
                // 요청에 대한 권한 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // /h2-console 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/members/**")).permitAll() // /members 경로에 대한 접근 허용 (회원가입 및 로그인)
                        .requestMatchers(new AntPathRequestMatcher("/main")).permitAll() // /main 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll() // /css 경로에 대한 접근 허용 (스타일시트)
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll() // /js 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll() // /images 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/img/**")).permitAll() // /img 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/templates/**")).permitAll() // /templates 경로에 대한 접근 허용 (템플릿 파일)
                        .requestMatchers(new AntPathRequestMatcher("/item/**")).permitAll() // /item 경로에 대한 접근
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                        .anyRequest().authenticated() // 위에서 명시한 경로 외의 모든 요청은 인증된 사용자만 접근 가능
                )
                //권한에 맞지 않는 사용자가 리소스에 접근 시 수행되는 핸들러
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                // CSRF 설정
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")) // /h2-console 경로에 대해 CSRF 보호 비활성화
                )
                // 폼 로그인 설정
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login") // 사용자 정의 로그인 페이지
                        .failureHandler(new CustomAuthenticationFailureHandler()) //로그인 실패시 처리하는 핸들러 설정
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 기본적으로 이동할 URL
                        .usernameParameter("email") // 로그인 시 사용자의 이메일을 사용자명으로 사용
                        .failureUrl("/members/login/error") // 로그인 실패 시 이동할 URL
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 요청을 처리할 URL
                        .logoutSuccessUrl("/main") // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                );
        return http.build(); // SecurityFilterChain 객체 반환
    }

    // PasswordEncoder Bean 정의 (BCryptPasswordEncoder 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
