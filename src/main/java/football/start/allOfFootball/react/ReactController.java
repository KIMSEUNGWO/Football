package football.start.allOfFootball.react;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import static football.common.consts.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:3000")
public class ReactController {

    private final ReactService reactService;

    @GetMapping("/member/get")
    public ResponseEntity<MemberInfo> getMember(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {
        System.out.println("React Test Code : " + memberId);
        MemberInfo memberInfo = reactService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }
}
