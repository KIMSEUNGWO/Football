package football.start.allOfFootball.react;

import football.common.domain.Member;
import football.common.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactService {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberInfo getMemberInfo(Long memberId) {
        if (memberId == null) return new MemberInfo();

        Optional<Member> byId = jpaMemberRepository.findById(memberId);

        return byId.map(x -> MemberInfo.builder()
                .hasSession(true)
                .name(x.getMemberName())
                .build())
            .orElse(new MemberInfo());
    }
}
