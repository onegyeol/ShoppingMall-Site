package project.wideWebsite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    private String uploadPath;//application.properties에 설정한 uploadpath 프로퍼티 값이 들어감

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){ //파일 업로드 경로 지정
        registry.addResourceHandler("/items/**") // 이 경로로 호출하는 자원은
                .addResourceLocations(uploadPath); // uploadPath 폴더 아래에서 찾는다.
    }
}
