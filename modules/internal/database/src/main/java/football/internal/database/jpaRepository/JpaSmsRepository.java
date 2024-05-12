package football.internal.database.jpaRepository;

import football.internal.database.domain.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSmsRepository extends JpaRepository<Sms, Long> {
    Optional<Sms> findByPhoneAndCertificationNumber(String phone, String certificationNumber);
    void deleteAllByPhone(String phone);
}
