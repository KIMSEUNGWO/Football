package football.common.jpaRepository;

import football.common.domain.Admin;
import football.common.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByMember(Member member);
}
