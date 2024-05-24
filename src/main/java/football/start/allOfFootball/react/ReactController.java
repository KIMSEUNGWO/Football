package football.start.allOfFootball.react;

import football.common.domain.Member;
import football.login.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:3000")
public class ReactController {

    private final ReactService reactService;

    @GetMapping("/member/get")
    public ResponseEntity<MemberInfo> getMember(@AuthenticationPrincipal PrincipalDetails user) {
        Member member = user.getMember();
        System.out.println("React Test Code : " + member.getMemberId());
        MemberInfo memberInfo = reactService.getMemberInfo(member.getMemberId());
        return ResponseEntity.ok(memberInfo);
    }
}
