package project.wideWebsite.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//@Entity로 관리되는 JPA 엔터티들의 상태 변화를 감시하는 기능을 킨다는 뜻
@EnableJpaAuditing
public class JpaConfig {
    //@PersistenceContext는 EntityManager에 의존성 주입을  담당하는 Annotation이다.
    @PersistenceContext
    //EntityManager는 JPA에서 엔터티의 생성, 조회, 수정 삭제를 수행하는 객체이다
    private EntityManager em;
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em){
        //쿼리를 작성하는 JPAQueryFactory에 EntityManager를 넘겨 사용한다.
        return new JPAQueryFactory(em);
    }
}
