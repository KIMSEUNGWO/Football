package football.start.allOfFootball.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스가 이 클래스를 상속받은 경 이 클래스의 필를 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // 해당 어노테이션이 붙은 클래스에 Auditing 기능을 포함시킨다.
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createDate;

}
