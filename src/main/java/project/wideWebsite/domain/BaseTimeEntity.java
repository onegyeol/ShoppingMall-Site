package project.wideWebsite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    //엔티티 처음 저장 시 자동으로 등록일 설정
    //엔티티가 업데이트될 때 변경되지 않고, 데이터베이스 컬럼의 값은 수정할 수 없음
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    //엔티티 마지막으로 수정 시 자동으로 수정일 설정
    //엔티티 생성될 땐 설정되지 않고 엔티티 업데이트될 때 값 변경됨
    @LastModifiedDate
    private LocalDateTime updateTime;
}
