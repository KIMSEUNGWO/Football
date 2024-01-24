package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSmsRepository extends JpaRepository<Sms, Long> {
    Optional<Sms> findByPhoneAndCertificationNumber(String phone, String certificationNumber);

    void deleteByPhone(String phone);
}
