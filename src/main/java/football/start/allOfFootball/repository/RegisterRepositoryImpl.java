package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import football.start.allOfFootball.jpaRepository.JpaSocialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RegisterRepositoryImpl implements RegisterRepository{

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaSocialRepository jpaSocialRepository;

    @Override
    public Optional<Member> findByMemberEmail(String email) {
        return jpaMemberRepository.findByMemberEmail(email);
    }

    @Override
    public void save(Member member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public void saveSocial(Social saveSocial) {
        jpaSocialRepository.save(saveSocial);
    }
}
