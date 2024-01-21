package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Admin;
import football.start.allOfFootball.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByMember(Member member);
}
