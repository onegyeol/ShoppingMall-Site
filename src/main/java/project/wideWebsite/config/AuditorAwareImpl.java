package project.wideWebsite.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 현재 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 있을 경우 사용자의 이름을 반환하고, 없을 경우 빈 문자열을 반환
        String userId = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anonymous"; // 또는 적절한 기본 값

        return Optional.of(userId);
    }
}
