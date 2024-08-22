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
        String userId = "";

        if(authentication != null && authentication.isAuthenticated()){
            userId = authentication.getName();  // getName() 메서드 결과를 userId에 할당
        }

        return Optional.of(userId);
    }
}
