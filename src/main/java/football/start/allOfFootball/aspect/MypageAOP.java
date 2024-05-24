package football.start.allOfFootball.aspect;

import football.common.domain.Member;
import football.login.config.auth.PrincipalDetails;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.service.MypageService;
import football.common.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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

        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
                Member member = user.getMember();
                Model model = getModel(joinPoint.getArgs());

                MyProfileDto myProfileDto = mypageService.getMyProfile(member);
                String location = getMenu(request);

                if (model != null) {
                    model.addAttribute("member", member);
                    model.addAttribute("profile", myProfileDto);
                    model.addAttribute("menu", location);
                }
            }
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
