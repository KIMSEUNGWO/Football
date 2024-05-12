package football.internal.database.jpaRepository;

import football.internal.database.domain.Admin;
import football.internal.database.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByMember(Member member);
}
