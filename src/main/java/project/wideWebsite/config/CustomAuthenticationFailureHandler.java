package project.wideWebsite.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        // 인증 실패 시 사용자에게 오류 메시지와 함께 로그인 페이지로 리다이렉트
        request.getSession().setAttribute("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
        response.sendRedirect("/members/login?error");
    }
}
