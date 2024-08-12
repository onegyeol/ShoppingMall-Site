package project.wideWebsite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity extends BaseTimeEntity{ //BaseTimeEntity 상속

    //엔티티 처음 저장 시 자동으로 생성자 설정
    //엔티티 업데이트될 때 변경 X, 데이터베이스 컬럼의 값은 수정 불가능
    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    //엔티티 마지막 수정 시 자동으로 수정자 설정
    //이 필드는 엔티티 생성될 댄 설정 X, 엔티티 수정 시 값 변경
    @LastModifiedBy
    private String modifiedBy;

}
