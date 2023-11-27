package football.start.allOfFootball.controller;

import football.start.allOfFootball.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String main(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {
        return "main";
    }
}
