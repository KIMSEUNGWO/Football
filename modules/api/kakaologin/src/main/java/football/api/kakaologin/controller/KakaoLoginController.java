package football.api.kakaologin.controller;

import football.api.kakaologin.service.KakaoLoginService;
import football.common.domain.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static football.common.enums.SocialEnum.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/logout/kakao/{memberId}")
    public String kakaoLogout(@PathVariable Long memberId) {
        Token findToken = kakaoLoginService.findByKakaoToken(memberId, KAKAO);
        if (findToken == null) return "redirect:/";

        kakaoLoginService.logout(findToken);
        return "redirect:" + kakaoLoginService.serviceLogout();
    }
}
