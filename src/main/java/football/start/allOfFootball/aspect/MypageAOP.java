package football.start.allOfFootball.aspect;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class MypageAOP {

    private final MemberService memberService;
    private final MypageService mypageService;

    @Before("execution(public * football.start.allOfFootball.controller.mypage.MypageController.*(..))")
    public void footerMyPageAspect(JoinPoint joinPoint) {

        log.info("MyPageController Aspect Before 실행 : {}", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();

        Long memberId = getMemberId(args);
        Model model = getModel(args);
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return;

        Member member = byMemberId.get();
        MyProfileDto myProfileDto = mypageService.getMyProfile(member);

        String location = getMypageLocation();

        model.addAttribute("profile", myProfileDto);
        model.addAttribute("menu", location);
    }

    private Model getModel(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Model) {
                return (Model) arg;
            }
        }
        return null;
    }

    private Long getMemberId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }

    private String getMypageLocation() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 요청된 URL 가져오기
        String requestUrl = request.getRequestURL().toString();
        int lastIndex = requestUrl.lastIndexOf("/") + 1;
        String location = requestUrl.substring(lastIndex);
        if (location.equals("") || location.equals("mypage")) {
            return "main";
        }
        return location;
    }
}
