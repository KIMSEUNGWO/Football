package football.start.allOfFootball.aspect;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Model model = getModel(joinPoint.getArgs());

        Member member = memberService.findByMemberId(memberId).get();
        MyProfileDto myProfileDto = mypageService.getMyProfile(member);
        String location = getMenu(request);

        if (model != null) {
            model.addAttribute("member", member);
            model.addAttribute("profile", myProfileDto);
            model.addAttribute("menu", location);
        }
    }

    private Model getModel(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Model) {
                return (Model) arg;
            }
        }
        return null;
    }

    private String getMenu(HttpServletRequest request) {
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
